package com.zeal.zealsay.converter;

import com.zeal.zealsay.dto.request.DictAddRequest;
import com.zeal.zealsay.dto.request.DictSaveRequest;
import com.zeal.zealsay.dto.response.DictResponse;
import com.zeal.zealsay.entity.Dict;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * user相关转换器.
 *
 * @author  zhanglei
 * @date 2018/11/15  5:43 PM
 */
@Mapper(componentModel = "spring")
public interface DictConvertMapper {

  DictResponse toDictResponse(Dict dict);

  Dict toDict(DictAddRequest dictAddRequest);

  List<DictResponse> toDictResponseList(List<Dict> dicts);

  List<Dict> toDictList(List<DictSaveRequest> dictSaveRequests);
}
