package com.easyim.common.config;

/**
 * @创建人: zhangyapo
 * @创建时间: 2018/9/28 09:58
 * @描述:
 */

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="message")
@PropertySource("classpath:message.properties")
public class MessageConfig {

    private String subcribe_server;
    private String publish_server;

    public String getSubcribe_server() {
        return subcribe_server;
    }

    public void setSubcribe_server(String subcribe_server) {
        this.subcribe_server = subcribe_server;
    }

    public String getPublish_server() {
        return publish_server;
    }

    public void setPublish_server(String publish_server) {
        this.publish_server = publish_server;
    }
}
