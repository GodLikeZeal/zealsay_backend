package com.zeal.zealsay.helper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.dto.request.FriendLinkRequest;
import com.zeal.zealsay.entity.FriendLink;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 友联帮助类.
 *
 * @author zhanglei
 * @date 2018/11/15  6:53 PM
 */
@Component
public class FriendLinkHelper {

  /**
   * 添加前初始化.
   *
   * @author zhanglei
   * @date 2019-08-23  14:44
   */
  public FriendLink initBeforeAdd(FriendLinkRequest friendLinkRequest) {
    FriendLink friendLink = new FriendLink();
    BeanUtils.copyProperties(friendLinkRequest,friendLink);
    friendLink.setCreateDate(LocalDateTime.now());
    return friendLink;
  }

  /**
   * 更新前初始化.
   *
   * @author zhanglei
   * @date 2019-08-23  14:44
   */
  public FriendLink initBeforeUpdate(FriendLinkRequest friendLinkRequest) {
    FriendLink friendLink = new FriendLink();
    BeanUtils.copyProperties(friendLinkRequest,friendLink);
    return friendLink;
  }

  /**
   * 转换成返回列表.
   *
   * @param friendLinkPage
   * @author zhanglei
   * @date 2018/11/15  9:25 PM
   */
  public PageInfo<FriendLink> toPageInfo(Page<FriendLink> friendLinkPage) {
    PageInfo<FriendLink> articlePageInfo = new PageInfo(friendLinkPage);
    return articlePageInfo;
  }
}
