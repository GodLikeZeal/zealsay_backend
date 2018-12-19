
package com.zeal.zealsay.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取urls.
 *
 * @author  zhanglei
 * @date 2018/9/14  下午7:57
 */
@Data
@Configuration
@ConditionalOnExpression("!'${ignore}'.isEmpty()")
@ConfigurationProperties(prefix = "ignore")
public class FilterIgnorePropertiesConfig {
    private List<String> urls = new ArrayList<>();

    private List<String> clients = new ArrayList<>();
}
