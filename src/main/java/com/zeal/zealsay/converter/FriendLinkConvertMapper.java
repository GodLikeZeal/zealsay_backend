package com.zeal.zealsay.converter;

import com.zeal.zealsay.dto.request.FriendLinkRequest;
import com.zeal.zealsay.entity.FriendLink;
import org.mapstruct.Mapper;

/**
 * friend_link相关转换器.
 *
 * @author  zhanglei
 * @date 2018/11/15  5:43 PM
 */
@Mapper(componentModel = "spring")
public interface FriendLinkConvertMapper {

  FriendLink toFriendLink(FriendLinkRequest friendLinkRequest);

}
