package com.zeal.zealsay.controller;



import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zeal.zealsay.common.entity.Result;
import com.zeal.zealsay.common.third.qiniu.QiniuService;
import com.zeal.zealsay.exception.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 牛云对象云存储
 * </p>
 *
 * @author zhanglei
 * @since 2018-11-28
 */
@Api(tags = "七牛云对象云存储模块")
@Slf4j
@RestController
@RequestMapping("/api/v1/qiniu")
public class QiniuController {

  @Autowired
  QiniuService qiniuService;

  /**
   * 上传图片.
   *
   * @author zhanglei
   * @date 2018/9/7  下午6:00
   */
  @PostMapping("/upload")
  @ApiOperation(value = "上传文件", notes = "上传文件")
  public Result<String> upload(@RequestParam MultipartFile file) {
    if (file.isEmpty()) {
      throw new ServiceException("上传文件失败");
    }
    try(InputStream in = file.getInputStream()) {
      log.info("开始上传文件到七牛云");
      return Result
          .of(qiniuService.uploadFile(in,qiniuService.createFileName(file)));
    } catch (IOException e) {
      log.error("上传文件到七牛云失败!");
      throw new ServiceException("上传文件失败");
    }

  }

  /**
   * 批量上传图片.
   *
   * @author zhanglei
   * @date 2018/9/7  下午6:00
   */
  @PostMapping("/upload/multiple")
  @ApiOperation(value = "批量上传文件", notes = "上传文件")
  public Result<List<Map<String,String>>> uploadMultiple(@RequestParam MultipartFile[] files) {
    if (files.length <= 0) {
      throw new ServiceException("上传文件失败");
    }
    List<Map<String,String>> results = Lists.newArrayList();
    for (MultipartFile file : files) {
      try (InputStream in = file.getInputStream()) {
        log.info("开始上传文件到七牛云");
        Map<String,String> map = Maps.newHashMap();
        map.put("pos",file.getOriginalFilename());
        map.put("url",qiniuService.uploadFile(in, qiniuService.createFileName(file)));
        results.add(map);
      } catch (IOException e) {
        log.error("上传文件到七牛云失败!");
        throw new ServiceException("上传文件失败");
      }
    }
    return Result.of(results);
  }


}

