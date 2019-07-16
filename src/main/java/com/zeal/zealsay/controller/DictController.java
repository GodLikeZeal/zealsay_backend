package com.zeal.zealsay.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import com.zeal.zealsay.common.entity.Result;
import com.zeal.zealsay.converter.DictConvertMapper;
import com.zeal.zealsay.entity.Dict;
import com.zeal.zealsay.service.DictService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 全局字典表 前端控制器
 * </p>
 *
 * @author zhanglei
 * @since 2019-03-27
 */
@Api(tags = "数据字典模块")
@Slf4j
@RestController
@RequestMapping("/api/v1/dict")
public class DictController {

  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  DictService dictService;
  @Autowired
  DictConvertMapper dictConvertMapper;

  @GetMapping("test")
  public Result generateDict() throws IOException {
    String content = Resources.toString(Resources.getResource("region/region.json"), Charsets.UTF_8);
    JSONArray array = JSON.parseArray(content);
    List<Dict> dicts = Lists.newArrayList();
    int sort = 1;
    int level = 1;
    setArray(dicts,array,null,sort,level);
    dictService.saveBatch(dicts);
    return Result.ok();
  }

  /**
   * 查询地区列表的省级别.
   *
   * @author  zhanglei
   * @date 2019-04-08  18:03
   */
  @GetMapping("region/province")
  public Result getProvinceList() {
    log.info("开始查询地区省的数据信息");
    return Result.of(dictConvertMapper
        .toDictResponseList(dictService.getProvinceList()));
  }

  /**
   * 查询地区列表的市级别.
   *
   * @author  zhanglei
   * @date 2019-04-08  18:03
   */
  @GetMapping("region/city")
  public Result getCityList(String code) {
    log.info("开始根据code{}查询地区市的数据信息",code);
    return Result.of(dictConvertMapper
        .toDictResponseList(dictService.getRegionList(code)));
  }

  /**
   * 查询地区列表的城市区区级别.
   *
   * @author  zhanglei
   * @date 2019-04-08  18:03
   */
  @GetMapping("region/area")
  public Result getAreaList(String code) {
    log.info("开始根据code{}查询地区城市区的数据信息",code);
    return Result.of(dictConvertMapper
        .toDictResponseList(dictService.getRegionList(code)));
  }

  private void setArray(List<Dict> dicts,JSONArray array,Integer parentId, int sort, Integer level) {
    for (JSONObject jsonObject : array.toJavaList(JSONObject.class)) {
      dicts.add(Dict.builder()
          .code(jsonObject.getInteger("code"))
          .name(jsonObject.getString("name"))
          .type("REGION")
          .parentCode(parentId)
          .sort(sort++)
          .description("全国省市区字典")
          .build());
      if (Objects.nonNull(jsonObject.get("cityList"))) {
        JSONArray se = (JSONArray) jsonObject.get("cityList");
        setArray(dicts,se,jsonObject.getInteger("code"),sort,level);
      }
      if (Objects.nonNull(jsonObject.get("areaList"))) {
        JSONArray se = (JSONArray) jsonObject.get("areaList");
        setArray(dicts,se,jsonObject.getInteger("code"),sort,level);
      }
    }
  }
}

