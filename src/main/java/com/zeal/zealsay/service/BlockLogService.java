package com.zeal.zealsay.service;

import com.zeal.zealsay.common.constant.enums.BlockAction;
import com.zeal.zealsay.common.constant.enums.BlockType;
import com.zeal.zealsay.entity.Article;
import com.zeal.zealsay.entity.BlockLog;
import com.zeal.zealsay.entity.User;
import com.zeal.zealsay.exception.ServiceException;
import com.zeal.zealsay.mapper.BlockLogMapper;
import com.zeal.zealsay.service.auth.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 封禁解封记录表 服务实现类
 * </p>
 *
 * @author zhanglei
 * @since 2019-03-16
 */
@Transactional(rollbackFor = {ServiceException.class,RuntimeException.class,Exception.class})
@Service
public class BlockLogService extends AbstractService<BlockLogMapper, BlockLog> {

    @Autowired
    UserDetailServiceImpl userDetailService;

    /**
    * 封禁解封记录.
    *
    * @author  zeal
    * @date 2019/3/17 22:23
    */
    public Boolean saveBlocak(User user, BlockType blockType, BlockAction blockAction, String reason) {
        return save(BlockLog.builder()
                .operatorId(userDetailService.getCurrentUser().getUserId())
                .operatorDate(LocalDateTime.now())
                .type(blockType)
                .action(blockAction)
                .reason(reason)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(3))
                .targetId(user.getId())
                .targetName(user.getUsername())
                .build());
    }


    /**
     * 批量保存用户封禁封禁记录.
     *
     * @author  zeal
     * @date 2019/3/17 22:23
     */
    public Boolean saveBlocakUserBatch(List<User> users, BlockType blockType, BlockAction blockAction,String reason) {
        return saveBatch(users.stream().map(user -> BlockLog.builder()
                .operatorId(userDetailService.getCurrentUser().getUserId())
                .operatorDate(LocalDateTime.now())
                .type(blockType)
                .action(blockAction)
                .reason(reason)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(3))
                .targetId(user.getId())
                .targetName(user.getUsername())
                .build())
                .collect(Collectors.toList()));
    }

    /**
     * 文章作品记录.
     *
     * @author  zeal
     * @date 2019/3/17 22:23
     */
    public Boolean saveBlocak(Article article, BlockType blockType, BlockAction blockAction, String reason) {
        return save(BlockLog.builder()
            .operatorId(userDetailService.getCurrentUser().getUserId())
            .operatorDate(LocalDateTime.now())
            .type(blockType)
            .action(blockAction)
            .reason(reason)
            .startDate(LocalDateTime.now())
            .endDate(LocalDateTime.now().plusDays(3))
            .targetId(article.getId())
            .targetName(article.getTitle())
            .build());
    }

    /**
     * 批量保存文章作品操作记录.
     *
     * @author  zhanglei
     * @date 2019-05-15  11:33
     */
    public Boolean saveBlocakArticleBatch(List<Article> articles, BlockType blockType, BlockAction blockAction,String reason) {
        return saveBatch(articles.stream().map(user -> BlockLog.builder()
            .operatorId(userDetailService.getCurrentUser().getUserId())
            .operatorDate(LocalDateTime.now())
            .type(blockType)
            .action(blockAction)
            .reason(reason)
            .startDate(LocalDateTime.now())
            .endDate(LocalDateTime.now().plusDays(3))
            .targetId(user.getId())
            .targetName(user.getTitle())
            .build())
            .collect(Collectors.toList()));
    }
}
