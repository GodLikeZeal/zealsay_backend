package com.zeal.zealsay.dto.response;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.zeal.zealsay.common.constant.enums.Role;
import com.zeal.zealsay.common.constant.enums.UserStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 数据字典返回对象.
 *
 * @author  zhanglei
 * @date 2018/11/15  6:39 PM
 */
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictResponse {

  @ApiModelProperty(value = "编码")
  private Integer code;

  @ApiModelProperty(value = "名称")
  private String name;

  @ApiModelProperty(value = "拼音简称")
  private String enShort;

  @ApiModelProperty(value = "父层code")
  private Integer parentCode;

  @ApiModelProperty(value = "类型")
  private String type;

  @ApiModelProperty(value = "描述")
  private String description;

  @ApiModelProperty(value = "顺序")
  private Integer sort;
}
