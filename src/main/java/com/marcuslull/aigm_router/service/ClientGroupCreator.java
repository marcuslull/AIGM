package com.marcuslull.aigm_router.service;

import com.marcuslull.aigm_router.model.AIClientGroup;
import org.springframework.stereotype.Service;

import static com.marcuslull.aigm_router.model.enums.AIName.*;

@Service
public class ClientGroupCreator {

    public ClientGroupCreator(AIClientFactory aiClientFactory, AIClientGroup aiClientGroup) {

            aiClientGroup.addModel(ORATORIX, aiClientFactory.createAiModel(ORATORIX));
            aiClientGroup.addModel(ORBIS, aiClientFactory.createAiModel(ORBIS));
            aiClientGroup.addModel(CHRONOS, aiClientFactory.createAiModel(CHRONOS));
            aiClientGroup.addModel(JUSTIVOR, aiClientFactory.createAiModel(JUSTIVOR));
            aiClientGroup.addModel(CONTINUITY, aiClientFactory.createAiModel(CONTINUITY));
    }
}
