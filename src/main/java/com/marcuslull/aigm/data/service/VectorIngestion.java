package com.marcuslull.aigm.data.service;

import com.marcuslull.aigm.data.DataIngestion;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class VectorIngestion implements DataIngestion {
    private final VectorStore vectorStore;

    @Value("classpath:/ingestion/srd51.pdf")
    Resource resource;
    String source = "srd51.pdf";
    String session = "0";
    String tag = "officialRules";

    @Autowired
    public VectorIngestion(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void ingest() {

        TikaDocumentReader reader = new TikaDocumentReader(resource);
        TextSplitter textSplitter = new TokenTextSplitter();

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("source", source);
        metadata.put("session", session);
        metadata.put("tag", tag);

        List<Document> fromSplitter = textSplitter.apply(reader.get());
        List<Document> fromMetadataAdder = addCustomMetadata(metadata, fromSplitter);

        vectorStore.accept(fromMetadataAdder);
        System.out.println("Vector store loaded.");
    }

    private List<Document> addCustomMetadata(Map<String, Object> metadata, List<Document> fromSplitter) {
        return fromSplitter.stream().map(document ->
                        new Document(document.getId(), Objects.requireNonNull(document.getText()), metadata))
                .collect(Collectors.toList());
    }
}
