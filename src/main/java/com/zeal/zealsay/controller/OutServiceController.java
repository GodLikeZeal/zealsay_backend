package com.zeal.zealsay.controller;

import com.zeal.zealsay.common.entity.Result;
import com.zeal.zealsay.feign.HitokotoClient;
import com.zeal.zealsay.feign.response.HitokotoResponse;
import com.zeal.zealsay.service.PhraseService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
* 外部服务接口.
*
* @author  zeal
* @date 2019/6/30 12:22
*/
@Api(tags = "第三方服务模块")
@Slf4j
@RestController
@RequestMapping("/api/v1/service")
public class OutServiceController {
    @Autowired
    PhraseService phraseService;

    /**
    * 一言获取接口.
    *
    * @author  zeal
    * @date 2019/6/30 12:56
    */
    @GetMapping("hitokoto")
    Result<HitokotoResponse> getHitokoto() throws ExecutionException, InterruptedException {
        log.info("开始查询一言接口");
        HitokotoResponse response = phraseService.get().get();
        log.info("本次返回一言为{}",response);
        return Result.of(response);
    }
}
