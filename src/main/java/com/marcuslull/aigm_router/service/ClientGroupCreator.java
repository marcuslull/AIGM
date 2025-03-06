package com.marcuslull.aigm_router.service;

import com.marcuslull.aigm_router.model.AIClientGroup;
import org.springframework.stereotype.Service;

import static com.marcuslull.aigm_router.model.AIClientTypes.*;

@Service
public class ClientGroupCreator {

    public ClientGroupCreator(AIClientFactory AIClientFactory, AIClientGroup AIClientGroup) {

        AIClientGroup.addModel(ORATORIX, AIClientFactory.createAiModel(ORATORIX));
        AIClientGroup.addModel(ORBIS, AIClientFactory.createAiModel(ORBIS));
        AIClientGroup.addModel(CHRONOS, AIClientFactory.createAiModel(CHRONOS));
        AIClientGroup.addModel(JUSTIVOR, AIClientFactory.createAiModel(JUSTIVOR));
        AIClientGroup.addModel(CONTINUITY, AIClientFactory.createAiModel(CONTINUITY));
    }
}
