package com.zeal.zealsay.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import com.zeal.zealsay.common.entity.Result;
import com.zeal.zealsay.entity.Dict;
import com.zeal.zealsay.service.DictService;
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
@RestController
@RequestMapping("/api/v1/dict")
public class DictController {

  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  DictService dictService;

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

