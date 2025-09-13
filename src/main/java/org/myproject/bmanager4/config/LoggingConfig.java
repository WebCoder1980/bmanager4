package org.myproject.bmanager4.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.myproject.bmanager4.Bmanager4Application;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfig {
    @Bean
    public Logger logger() {
        return LogManager.getLogger(Bmanager4Application.class);
    }
}
