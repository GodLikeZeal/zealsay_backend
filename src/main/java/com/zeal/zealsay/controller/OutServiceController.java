package com.zeal.zealsay.controller;

import com.zeal.zealsay.common.entity.Result;
import com.zeal.zealsay.feign.HitokotoClient;
import com.zeal.zealsay.feign.response.HitokotoResponse;
import com.zeal.zealsay.service.PhraseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* 外部服务接口.
*
* @author  zeal
* @date 2019/6/30 12:22
*/
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
    Result<HitokotoResponse> getHitokoto() {
        return Result.of(phraseService.get());
    }
}
