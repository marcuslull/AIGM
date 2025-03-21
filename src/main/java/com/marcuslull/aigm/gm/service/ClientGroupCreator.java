package com.marcuslull.aigm.gm.service;

import com.marcuslull.aigm.gm.model.AIClientGroup;
import org.springframework.stereotype.Service;

import static com.marcuslull.aigm.gm.model.enums.AIName.*;


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
