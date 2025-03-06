package com.marcuslull.aigm_router.config;

import com.marcuslull.aigm_router.model.AIClientGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIClientConfig {

    @Bean
    public AIClientGroup aiClientGroup() {
        return new AIClientGroup();
    }
}
