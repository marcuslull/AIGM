package com.marcuslull.aigm.gm.service;

import com.google.cloud.vertexai.VertexAI;
import com.marcuslull.aigm.gm.GmGroupCreator;
import com.marcuslull.aigm.gm.model.AIClientGroup;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.marcuslull.aigm.gm.model.enums.AIName.*;

@Service
public class AIGmGroupCreator implements GmGroupCreator {

    AIClientFactory aiClientFactory;

    @Autowired
    public AIGmGroupCreator(VectorStore vectorStore, VertexAI vertexAI) {
        aiClientFactory = new AIClientFactory(vectorStore, vertexAI);
    }

    @Override
    public void create() {

        AIClientGroup.addModel(ORATORIX, aiClientFactory.createAiModel(ORATORIX));
        AIClientGroup.addModel(ORBIS, aiClientFactory.createAiModel(ORBIS));
        AIClientGroup.addModel(CHRONOS, aiClientFactory.createAiModel(CHRONOS));
        AIClientGroup.addModel(JUSTIVOR, aiClientFactory.createAiModel(JUSTIVOR));
        AIClientGroup.addModel(CONTINUITY, aiClientFactory.createAiModel(CONTINUITY));
    }
}
