package com.zeal.zealsay.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统配置保存对象.
 *
 * @author  zhanglei
 * @date 2018/11/15  6:39 PM
 */
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DictSaveRequest {

  private Long id;

  private Integer code;

  private String name;

}
