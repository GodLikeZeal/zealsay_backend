package com.zeal.zealsay.service;

import com.zeal.zealsay.common.constant.enums.BlockAction;
import com.zeal.zealsay.entity.BlockLog;
import com.zeal.zealsay.entity.User;
import com.zeal.zealsay.exception.ServiceException;
import com.zeal.zealsay.mapper.BlockLogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class BlockLogService extends ServiceImpl<BlockLogMapper, BlockLog> {

    @Autowired
    UserDetailServiceImpl userDetailService;

    /**
    * 封禁记录.
    *
    * @author  zeal
    * @date 2019/3/17 22:23
    */
    public Boolean saveBlocak(User user,BlockAction blockAction,String reason) {
        return save(BlockLog.builder()
                .operatorId(userDetailService.getCurrentUser().getUserId())
                .operatorDate(LocalDateTime.now())
                .action(blockAction)
                .reason(reason)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(3))
                .targetId(user.getId())
                .targetName(user.getUsername())
                .build());
    }


    /**
     * 批量保存封禁记录.
     *
     * @author  zeal
     * @date 2019/3/17 22:23
     */
    public Boolean saveBlocakBatch(List<User> users,BlockAction blockAction,String reason) {
        return saveBatch(users.stream().map(user -> BlockLog.builder()
                .operatorId(userDetailService.getCurrentUser().getUserId())
                .operatorDate(LocalDateTime.now())
                .action(blockAction)
                .reason(reason)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(3))
                .targetId(user.getId())
                .targetName(user.getUsername())
                .build())
                .collect(Collectors.toList()));
    }
}
