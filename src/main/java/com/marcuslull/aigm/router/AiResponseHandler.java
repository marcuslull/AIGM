package com.marcuslull.aigm.router;

import com.marcuslull.aigm.router.model.AiResponse;

public interface AiResponseHandler {
    void handle(AiResponse aiResponse);
    boolean canHandle(AiResponse aiResponse);
}
