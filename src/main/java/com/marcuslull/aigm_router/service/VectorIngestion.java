package com.marcuslull.aigm_router.service;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class VectorIngestion {
    private final VectorStore vectorStore;

    @Value("classpath:/vectorImportDocs/chase.pdf")
    Resource resource;

    public VectorIngestion(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public void ingestDocsFolder() {
        TikaDocumentReader reader = new TikaDocumentReader(resource);
        TextSplitter textSplitter = new TokenTextSplitter();
        vectorStore.accept(textSplitter.apply(reader.get()));
        System.out.println("Vector store loaded.");
    }
}
