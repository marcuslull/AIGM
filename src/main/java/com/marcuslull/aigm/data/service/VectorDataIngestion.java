package com.marcuslull.aigm.data.service;

import com.marcuslull.aigm.data.DataIngestion;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;


@Service
public class VectorDataIngestion implements DataIngestion {
    private final VectorStore vectorStore;

    @Value("classpath:/ingestion/SRD_CC_v5.1.pdf")
    Resource resource;

    @Autowired
    public VectorDataIngestion(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void ingest() {
        TikaDocumentReader reader = new TikaDocumentReader(resource);
        TextSplitter textSplitter = new TokenTextSplitter();
        vectorStore.accept(textSplitter.apply(reader.get()));
        System.out.println("Vector store loaded.");
    }
}
