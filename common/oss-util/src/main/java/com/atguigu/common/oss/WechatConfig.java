package com.atguigu.common.oss;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"classpath:application-wechat.properties"})
@ConfigurationProperties(prefix = "wechat2")
@Data
public class WechatConfig {
    public String url;
    public String ttp;
}
