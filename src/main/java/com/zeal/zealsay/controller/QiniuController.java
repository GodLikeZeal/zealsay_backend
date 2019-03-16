package com.zeal.zealsay.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiniu.http.Response;
import com.zeal.zealsay.common.entity.PageInfo;
import com.zeal.zealsay.common.entity.Result;
import com.zeal.zealsay.common.third.qiniu.QiniuService;
import com.zeal.zealsay.converter.ArticleConvertMapper;
import com.zeal.zealsay.dto.request.ArticleAddRequest;
import com.zeal.zealsay.dto.request.ArticlePageRequest;
import com.zeal.zealsay.dto.request.ArticleUpdateRequest;
import com.zeal.zealsay.dto.response.ArticleResponse;
import com.zeal.zealsay.entity.Article;
import com.zeal.zealsay.exception.ServiceException;
import com.zeal.zealsay.helper.ArticleHelper;
import com.zeal.zealsay.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

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
   * 根据id来查询.
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


}

