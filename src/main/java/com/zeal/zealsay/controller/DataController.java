package com.zeal.zealsay.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.ImmutableMap;
import com.zeal.zealsay.common.constant.enums.ResultCode;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.common.entity.Result;
import com.zeal.zealsay.common.entity.SecuityUser;
import com.zeal.zealsay.dto.request.*;
import com.zeal.zealsay.dto.response.*;
import com.zeal.zealsay.entity.*;
import com.zeal.zealsay.exception.ServiceException;
import com.zeal.zealsay.feign.response.HitokotoResponse;
import com.zeal.zealsay.helper.ArticleHelper;
import com.zeal.zealsay.helper.ArticleLikeHelper;
import com.zeal.zealsay.helper.UserHelper;
import com.zeal.zealsay.service.*;
import com.zeal.zealsay.service.auth.UserDetailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * 页面数据服务入口.
 *
 * @author zhanglei
 * @date 2018/11/15  6:56 PM
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/data")
public class DataController {

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    EmailService emailService;
    @Autowired
    ArticleService articleService;
    @Autowired
    BlockLogService blockLogService;
    @Autowired
    DictService dictService;
    @Autowired
    PhraseService phraseService;
    @Autowired
    CommentService commentService;
    @Autowired
    ArticleLabelService articleLabelService;
    @Autowired
    ArticleLikeService articleLikeService;
    @Autowired
    ArticleCategoryService articleCategoryService;
    @Autowired
    ArticleHelper articleHelper;
    @Autowired
    UserDetailServiceImpl userDetailService;
    @Autowired
    ArticleLikeHelper articleLikeHelper;
    @Autowired
    UserHelper userHelper;

    /**
     * 首页信息获取.
     *
     * @author zhanglei
     * @date 2018/9/7  下午6:00
     */
    @GetMapping("/index")
    public Result<Map<String, Object>> getIndexData(ArticlePageRequest articlePageRequest) throws ExecutionException, InterruptedException {
        log.info("首页数据获取中...");
        //获取五篇火热文章
        List<ArticleResponse> hotArticles = articleService.getHotArticleList().get();
        //获取一言
        HitokotoResponse hitokoto = phraseService.get().get();
        //获取分类
        List<ArticleCategoryResponse> categorys = articleCategoryService.getCategoryList().get();
        //获取标签
        List<ArticleLabel> list = articleLabelService.list();
        List<ArticleLabelResponse> labels = null;
        if (!CollectionUtils.isEmpty(list)) {
            labels = list.stream().map(s -> {
                ArticleLabelResponse response = new ArticleLabelResponse();
                BeanUtils.copyProperties(s, response);
                return response;
            }).collect(Collectors.toList());
        }
        //获取文章列表
        Page<Article> articlePage = (Page<Article>) articleService
                .page(new Page<>(1, 10), articleHelper.toArticlePageRequestWrapperForC(articlePageRequest));
        PageInfo<ArticlePageResponse> pageInfo = articleHelper.toPageInfo(articlePage);

        //获取近期评论
        List<CommentResponse> comments = commentService.recentDiscuss();

        log.info("首页数据获取完毕");
        return Result
                .of(ImmutableMap.builder()
                        .put("pageInfo", pageInfo)
                        .put("hotArticles", hotArticles)
                        .put("hitokoto", hitokoto)
                        .put("labels", labels)
                        .put("categorys", categorys)
                        .put("comments", comments)
                        .build());
    }

    /**
     * 用户中心信息获取.
     *
     * @author zhanglei
     * @date 2020/6/11  4:34 下午
     */
    @GetMapping("/user/{id}")
    public Result<Map<String, Object>> getUserData(@PathVariable Long id) throws ExecutionException, InterruptedException {
        log.info("👕用户中心信息获取中...");
        //获取当前用户信息
        User userInfo = userService.getById(id);
        UserResponse user = new UserResponse();
        if (Objects.nonNull(userInfo)) {
            BeanUtils.copyProperties(userInfo,user);
        }
        if (Objects.isNull(user)) {
            throw new ServiceException(ResultCode.NOT_FOUND.getCode(), "用户不存在");
        }
        Page<Article> articlePage = articleService
                .page(new Page<>(1, 500), new QueryWrapper<Article>().eq("author_id", id));
        PageInfo<ArticlePageResponse> userPage = articleHelper.toPageInfo(articlePage);

        //获取收藏列表
        Page<ArticleLike> likePages = articleLikeService
                .page(new Page<>(1, 500), new QueryWrapper<ArticleLike>().eq("user_id", id));
        PageInfo<ArticleResponse> likePage = articleLikeHelper.toPageInfo(likePages);

        //获取动态
        List<BlockLog> actions = blockLogService.getUserActions(id);

        //获取省份
        List<Dict> provinces = dictService.getProvinceList().get();

        //获取分类
        List<ArticleCategoryResponse> categorys = articleCategoryService.getCategoryList().get();

        //角色信息
        List<Role> list = roleService.list();
        List<RoleResponse> roles = null;
        if (CollectionUtils.isEmpty(list)) {
            roles = list.stream().map(s -> {
                RoleResponse response = new RoleResponse();
                BeanUtils.copyProperties(s,response);
                return response;
            }).collect(Collectors.toList());
        }

        log.info("👕用户中心信息获取完毕");
        return Result
                .of(ImmutableMap.builder()
                        .put("user", user)
                        .put("userPage", userPage)
                        .put("likePage", likePage)
                        .put("actions", actions)
                        .put("categorys", categorys)
                        .put("roles", roles)
                        .put("province", provinces)
                        .build());
    }

    /**
     * 文章详情接口.
     *
     * @author zhanglei
     * @date 2020/6/17  6:49 下午
     */
    @GetMapping("/article/{id}")
    public Result<Map<String, Object>> getArticleDetail(@PathVariable Long id,
                                                        @RequestParam(defaultValue = "1") Long pageNumber,
                                                        @RequestParam(defaultValue = "10") Long pageSize) throws ExecutionException, InterruptedException {
        log.info("🌴文章详情页面数据展现...");

        //获取文章
        ArticleResponse article = articleHelper.toArticleResponse(articleService.getById(id));

        //获取分类
        List<ArticleCategoryResponse> categorys = articleCategoryService.getCategoryList().get();

        //获取评论人数
        long count = commentService.count(new QueryWrapper<Comment>().eq("article_id", id));
        //获取评论
        PageInfo<CommentResponse> commentPage = commentService.pageCommentList(pageNumber, pageSize, id, null);
        //判断是否喜欢过
        Boolean like = false;
        SecuityUser currentUser = userDetailService.getCurrentUser();
        if (Objects.nonNull(currentUser)) {
            like = articleLikeService.islike(id);
        }

        log.info("👕文章详情页面数据获取完毕");
        return Result
                .of(ImmutableMap.builder()
                        .put("article", article)
                        .put("commentPage", commentPage)
                        .put("like", like)
                        .put("categorys", categorys)
                        .put("count", count)
                        .build());
    }

    /**
     * 后台管理页面数据.
     *
     * @author zhanglei
     * @date 2020/6/12  2:28 下午
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EXPERIENCER')")
    @GetMapping("/admin/dashboard")
    public Result<Map<String, Object>> getDashboardData() {
        log.info("👕后台管理页面数据获取中...");
        long userNum = userService.countUser();
        long userAddNum = userService.countUserAdd();
        long blogNum = articleService.count();
        long blogAddNum = articleService.countArticleAdd();
        log.info("👕后台管理页面数据获取完毕");
        return Result
                .of(ImmutableMap.builder()
                        .put("userNum", userNum)
                        .put("userAddNum", userAddNum)
                        .put("blogNum", blogNum)
                        .put("blogAddNum", blogAddNum)
                        .build());
    }
}

