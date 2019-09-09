package com.zeal.zealsay.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zeal.zealsay.dto.request.FriendLinkRequest;
import com.zeal.zealsay.entity.FriendLink;
import com.zeal.zealsay.exception.ServiceException;
import com.zeal.zealsay.helper.FriendLinkHelper;
import com.zeal.zealsay.mapper.FriendLinkMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 友链记录表 服务实现类
 * </p>
 *
 * @author zhanglei
 * @since 2019-07-31
 */
@Slf4j
@Service
public class FriendLinkService extends ServiceImpl<FriendLinkMapper, FriendLink> {

  @Autowired
  FriendLinkHelper friendLinkHelper;

  /**
   * 添加友链.
   *
   * @author  zhanglei
   * @date 2019-08-23  14:55
   */
  public Boolean saveFriendLink(FriendLinkRequest friendLinkRequest) {
    //初始化
    FriendLink friendLink = friendLinkHelper.initBeforeAdd(friendLinkRequest);
    //验证是否重复
    if (existCheck(friendLinkRequest)) {
      throw new ServiceException("该友链已经添加过啦,把位置留给其他的小伙伴吧！");
    }
    //保存入库
    return save(friendLink);
  }

  /**
   * 添加友链.
   *
   * @author  zhanglei
   * @date 2019-08-23  14:55
   */
  public Boolean updateFriendLink(FriendLinkRequest friendLinkRequest) {
    //判断是否可更新
    if (checkBeforeUpdate(friendLinkRequest)) {
      throw new ServiceException("友链名称重复了,怎么回事,小老弟？");
    }

    //参数转换
    FriendLink friendLink = friendLinkHelper.initBeforeUpdate(friendLinkRequest);

    //保存入库
    return updateById(friendLink);
  }

  /**
   * 判断是否存在.
   *
   * @author zhanglei
   * @date 2019-08-23  14:48
   */
  private Boolean existCheck(FriendLinkRequest friendLinkRequest) {
    List<FriendLink> linkList = list(new QueryWrapper<FriendLink>().eq("friend_name", friendLinkRequest.getFriendName()));
    if (!CollectionUtils.isEmpty(linkList)) {
      return true;
    }
    return false;
  }

  /**
   * 判断友链是否重复.
   *
   * @author  zhanglei
   * @date 2019-09-03  15:03
   */
  private Boolean checkBeforeUpdate(FriendLinkRequest friendLinkRequest) {
    //判断友链名字是否重复
    if (Objects.isNull(friendLinkRequest.getId())) {
      return count(new QueryWrapper<FriendLink>()
          .eq("friend_name", friendLinkRequest.getFriendName())) > 0 ? true : false;
    } else {
      return count(new QueryWrapper<FriendLink>()
          .eq("friend_name", friendLinkRequest.getFriendName())
          .ne("id", friendLinkRequest.getId())) > 0 ? true : false;
    }
  }


}
