package com.zeal.zealsay.feign.fallback;

import com.zeal.zealsay.feign.HitokotoClient;
import com.zeal.zealsay.feign.response.HitokotoResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HitokotoFallBack implements HitokotoClient {
    @Override
    public HitokotoResponse get() {
        log.error("调用一言接口异常，url为https://v1.hitokoto.cn");
        return null;
    }
}
