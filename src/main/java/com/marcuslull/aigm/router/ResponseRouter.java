package com.marcuslull.aigm.router;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcuslull.aigm.router.model.AiResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResponseRouter {

    // Context class for strategy design pattern - handler interface = AiResponseHandler

    private final ObjectMapper mapper = new ObjectMapper();
    public final static List<AiResponseHandler> handlers = new ArrayList<>();

    public void addHandler(AiResponseHandler handler) {
        handlers.add(handler);
    }

    public void removeHandler(AiResponseHandler handler) {
        handlers.remove(handler);
    }

    public void route(AiResponse aiResponse) {

        if (handlers.isEmpty()) throw new RuntimeException("No response handlers available");

        for (AiResponseHandler handler : handlers) {
            if (handler.canHandle(aiResponse)) handler.handle(aiResponse);
        }
    }
}
