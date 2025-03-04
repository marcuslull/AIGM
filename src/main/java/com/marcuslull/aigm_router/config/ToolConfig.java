package com.marcuslull.aigm_router.config;

import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.vertexai.gemini.schema.VertexToolCallingManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToolConfig {

    @Bean
    ToolCallingManager toolCallingManager() {
        return ToolCallingManager.builder().build();
    }

    @Bean
    VertexToolCallingManager vertexToolCallingManager() {
        return new VertexToolCallingManager(toolCallingManager());
    }
}
