package com.zeal.zealsay.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.common.entity.Result;
import com.zeal.zealsay.converter.DictConvertMapper;
import com.zeal.zealsay.dto.request.DictAddRequest;
import com.zeal.zealsay.dto.request.DictRequest;
import com.zeal.zealsay.dto.request.DictSaveRequest;
import com.zeal.zealsay.entity.Dict;
import com.zeal.zealsay.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

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

    @ApiOperation(value = "自动生成省市区字典接口",notes = "生成省市区字典接口")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("test")
    public Result generateDict() throws IOException {
        String content = Resources.toString(Resources.getResource("region/region.json"), Charsets.UTF_8);
        JSONArray array = JSON.parseArray(content);
        List<Dict> dicts = Lists.newArrayList();
        int sort = 1;
        int level = 1;
        setArray(dicts, array, null, sort, level);
        dictService.saveBatch(dicts);
        return Result.ok();
    }

    /**
     * 查询地区列表的省级别.
     *
     * @author zhanglei
     * @date 2019-04-08  18:03
     */
    @ApiOperation(value = "查询全国省级辖区",notes = "查询全国省级辖区")
    @GetMapping("region/province")
    public Result getProvinceList() throws ExecutionException, InterruptedException {
        log.info("开始查询地区省的数据信息");
        return Result.of(dictConvertMapper
                .toDictResponseList(dictService.getProvinceList().get()));
    }

    /**
     * 查询地区列表的市级别.
     *
     * @author zhanglei
     * @date 2019-04-08  18:03
     */
    @ApiOperation(value = "查询全国市级辖区",notes = "查询全国市级辖区")
    @GetMapping("region/city")
    public Result getCityList(String code) throws ExecutionException, InterruptedException {
        log.info("开始根据code{}查询地区市的数据信息", code);
        return Result.of(dictConvertMapper
                .toDictResponseList(dictService.getRegionList(code).get()));
    }

    /**
     * 分页查询.
     *
     * @author zhanglei
     * @date 2018/9/7  下午6:00
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询字典列表", notes = "分页查询文分页查询字典列表章信息列表")
    public Result<PageInfo<Dict>> getByPaginate(@RequestParam(defaultValue = "1") Long pageNumber,
                                                @RequestParam(defaultValue = "10") Long pageSize,
                                                DictRequest dictRequest) {
        log.info("开始进行分页查询字典列表，查询参数为 '{}' ", dictRequest);
        Page<Dict> dictPage = dictService
                .page(new Page<>(pageNumber, pageSize), dictService.buildParams(dictRequest));
        return Result.of(PageInfo.<Dict>builder()
                .records(dictPage.getRecords())
                .currentPage(dictPage.getCurrent())
                .pageSize(dictPage.getSize())
                .total(dictPage.getTotal())
                .build());
    }

    /**
     * 查询type.
     *
     * @author zhanglei
     * @date 2018/9/7  下午6:00
     */
    @GetMapping("/type")
    @ApiOperation(value = "查询type", notes = "查询type的list")
    public Result<PageInfo<Dict>> getByTypeList(DictRequest dictRequest) {
        log.info("开始进行查询type列表，查询参数为 '{}' ", dictRequest);
        return Result.of(dictService.getTypeList(dictRequest));
    }


    /**
     * 新增字典.
     *
     * @author zhanglei
     * @date 2018/9/7  下午6:00
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("")
    @ApiOperation(value = "新增字典", notes = "新增字典")
    public Result<Boolean> saveDict(@RequestBody @Validated DictAddRequest dictRequest) {
        log.info("开始添加一条数据字典记录，参数为 '{}' ", dictRequest);
        return Result.of(dictService.save(dictRequest));
    }

    /**
     * 修改字典.
     *
     * @author zhanglei
     * @date 2018/9/7  下午6:00
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("")
    @ApiOperation(value = "修改字典", notes = "修改字典")
    public Result<Boolean> saveDict(@RequestBody Dict dict) {
        log.info("开始修改一条数据字典记录，参数为 '{}' ", dict);
        return Result.of(dictService.updateDictById(dict));
    }

    /**
     * 删除字典.
     *
     * @author zhanglei
     * @date 2018/9/7  下午6:00
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除字典", notes = "删除字典")
    public Result<Boolean> deleteDict(@PathVariable Long id) {
        log.info("删除字典，参数为 '{}' ", id);
        return Result.of(dictService.delete(id));
    }

    /**
     * 批量删除字典.
     *
     * @author zhanglei
     * @date 2018/9/7  下午6:00
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/batch")
    @ApiOperation(value = "批量删除字典", notes = "批量删除字典")
    public Result<Boolean> deleteDictBatch(@RequestBody List<Long> ids) {
        log.info("开始批量删除数据字典记录，参数为 '{}' ", ids);
        return Result.of(dictService.deleteBatch(ids));
    }

    /**
     * 查询地区列表的城市区区级别.
     *
     * @author zhanglei
     * @date 2019-04-08  18:03
     */
    @GetMapping("region/area")
    public Result getAreaList(String code) throws ExecutionException, InterruptedException {
        log.info("开始根据code{}查询地区城市区的数据信息", code);
        return Result.of(dictConvertMapper
                .toDictResponseList(dictService.getRegionList(code).get()));
    }

    private void setArray(List<Dict> dicts, JSONArray array, Integer parentId, int sort, Integer level) {
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
                setArray(dicts, se, jsonObject.getInteger("code"), sort, level);
            }
            if (Objects.nonNull(jsonObject.get("areaList"))) {
                JSONArray se = (JSONArray) jsonObject.get("areaList");
                setArray(dicts, se, jsonObject.getInteger("code"), sort, level);
            }
        }
    }
}

