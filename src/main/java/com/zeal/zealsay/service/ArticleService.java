package com.zeal.zealsay.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zeal.zealsay.common.constant.enums.ArticleStatus;
import com.zeal.zealsay.common.constant.enums.BlockAction;
import com.zeal.zealsay.common.constant.enums.BlockType;
import com.zeal.zealsay.dto.request.ArticleAddRequest;
import com.zeal.zealsay.dto.request.ArticleUpdateRequest;
import com.zeal.zealsay.dto.response.ArticleResponse;
import com.zeal.zealsay.entity.Article;
import com.zeal.zealsay.entity.User;
import com.zeal.zealsay.exception.ServiceException;
import com.zeal.zealsay.helper.ArticleHelper;
import com.zeal.zealsay.mapper.ArticleMapper;
import com.zeal.zealsay.service.auth.UserDetailServiceImpl;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author zhanglei
 * @since 2018-11-28
 */
@Slf4j
@Transactional(rollbackFor = {ServiceException.class, RuntimeException.class, Exception.class})
@Service
public class ArticleService extends AbstractService<ArticleMapper, Article> implements IService<Article> {

    @Autowired
    UserDetailServiceImpl userDetailService;
    @Autowired
    ArticleHelper articleHelper;
    @Autowired
    BlockLogService blockLogService;

    /**
     * 添加文章.
     *
     * @author zhanglei
     * @date 2018/12/29  5:07 PM
     */
    public Boolean addArticle(ArticleAddRequest articleAddRequest) {
        Article article = articleHelper.initBeforeAdd(articleAddRequest);
        List<Article> articles = list(new QueryWrapper<Article>().lambda()
                .eq(Article::getTitle, articleAddRequest.getTitle()));
        if (Objects.nonNull(articles) && articles.size() > 0) {
            throw new ServiceException("请勿重复添加文章");
        }
        return save(article);
    }

    /**
     * 修改文章.
     *
     * @author zhanglei
     * @date 2018/12/29  5:07 PM
     */
    public Boolean updateArticle(ArticleUpdateRequest articleUpdateRequest) {
        Article article = articleHelper.initBeforeUpdate(articleUpdateRequest);
        return updateById(article);
    }

    /**
     * 根据id来上架文章作品.
     *
     * @author zhanglei
     * @date 2019-05-15  11:16
     */
    public Boolean markArticleUp(Long id) {
        //记录
        Article article = getById(id);

        if (article.getStatus().equals(ArticleStatus.FORMAL)) {
            throw new ServiceException("该作品已发布，请不要重复操作上架");
        }

        blockLogService.saveBlocak(article, BlockType.ARTICLE, BlockAction.UP, "");

        return updateById(Article.builder()
                .id(id)
                .status(ArticleStatus.FORMAL)
                .build());
    }

    /**
     * 根据id来下架文章作品.
     *
     * @author zhanglei
     * @date 2019-05-15  11:16
     */
    public Boolean markArticleDown(Long id) {

        Article article = getById(id);

        if (article.getStatus().equals(ArticleStatus.DOWN)) {
            throw new ServiceException("该作品已下架，请不要重复操作下架");
        }
        if (article.getStatus().equals(ArticleStatus.DRAFT)) {
            throw new ServiceException("该作品还未发布，无法下架");
        }

        //记录
        blockLogService.saveBlocak(article, BlockType.ARTICLE, BlockAction.DOWN, "违禁");

        return updateById(Article.builder()
                .id(id)
                .status(ArticleStatus.DOWN)
                .build());
    }

    /**
     * 批量下架文章作品.
     *
     * @author zhanglei
     * @date 2019-05-15  11:30
     */
    public Boolean markArticleDown(@NonNull Collection<Long> ids) {

        List<Article> articles = (List<Article>) listByIds(ids);

        //记录
        blockLogService.saveBlocakArticleBatch(articles, BlockType.ARTICLE, BlockAction.BAN, "违禁");

        update(Article.builder().status(ArticleStatus.DOWN).build(), new UpdateWrapper<Article>()
                .in("id", ids));
        return true;
    }

    /**
     * 阅读数增加.
     *
     * @author zeal
     * @date 2019/10/29 23:05
     */
    public Boolean readArticle(Long articleId) {
        Article article = getById(articleId);
        if (Objects.isNull(article)) {
            log.warn("未能找到id为{}的文章信息", articleId);
            return false;
        }
        return updateById(Article.builder()
                .id(articleId)
                .readNum(article.getReadNum() + 1)
                .build());
    }

    /**
     * 今日新增blog.
     *
     * @author zhanglei
     * @date 2020/6/12  2:19 下午
     */
    public long countArticleAdd() {
        return count(new QueryWrapper<Article>().ge("create_date", LocalDateTime.now())
                .lt("create_date", LocalDateTime.now().plusDays(1)));
    }

    /**
     * 总blog数.
     *
     * @author zhanglei
     * @date 2020/6/12  2:19 下午
     */
    public long countArticle() {
        return count();
    }

    /**
     * 获取5篇热点文章
     * .
     *
     * @author zhanglei
     * @date 2020/6/10 21:03
     */
    @Async
    public Future<List<ArticleResponse>> getHotArticleList() {
        log.info("🔥火热文字获取中...");
        List<Article> list = list(new QueryWrapper<Article>()
                .select("id", "title", "subheading", "cover_image")
                .orderByDesc("read_num").last("limit 5"));
        if (CollectionUtils.isEmpty(list)) {
            return new AsyncResult<>(null);
        }
        List<ArticleResponse> responses = list.stream().map(r -> {
            ArticleResponse response = new ArticleResponse();
            BeanUtils.copyProperties(r, response);
            return response;
        }).collect(Collectors.toList());
        return new AsyncResult<>(responses);
    }
}
