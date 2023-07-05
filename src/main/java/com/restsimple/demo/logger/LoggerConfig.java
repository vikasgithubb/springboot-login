package com.restsimple.demo.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfig {

    @Bean
    public Logger getLogger() {
        return LoggerFactory.getLogger("your.package.name");
    }
}

