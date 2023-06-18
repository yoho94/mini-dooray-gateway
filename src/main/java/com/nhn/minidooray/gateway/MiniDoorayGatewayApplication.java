package com.nhn.minidooray.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableRedisHttpSession
@ConfigurationPropertiesScan("com.nhn.minidooray.gateway.config")
public class MiniDoorayGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniDoorayGatewayApplication.class, args);
    }

}
