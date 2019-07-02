package com.zeal.zealsay.feign;

import com.zeal.zealsay.feign.fallback.HitokotoFallBack;
import com.zeal.zealsay.feign.response.HitokotoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
* Hitokoto一言接口.
*
* @author  zeal
* @date 2019/6/30 12:00
*/
@FeignClient(url = "https://v1.hitokoto.cn",name = "HitokotoClient",fallback = HitokotoFallBack.class)
public interface HitokotoClient {

    @GetMapping("/")
    HitokotoResponse get ();
}
