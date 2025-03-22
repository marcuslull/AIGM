package com.marcuslull.aigm.data.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcuslull.aigm.data.model.ResonanceSearch;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionTextParser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResonanceRetrieval {

//    "resonanceSearch": {
//        "metaSearch": {
//            "source": "title1",
//            "session": "session1",
//            "tag": "tag1"
//        },
//        "textSearch": "text to search for"
//    }

    private final VectorStore vectorStore;
    private final ObjectMapper mapper;

    public ResonanceRetrieval(VectorStore vectorStore, ObjectMapper mapper) {
        this.vectorStore = vectorStore;
        this.mapper = mapper;
    }

    public List<Document> query(String query) throws JsonProcessingException {

        // TODO: add error handling
        ResonanceSearch resonanceSearch = mapper.readValue(query, ResonanceSearch.class);

        String sourceValue = resonanceSearch.metaSearch().source();
        String sessionValue = resonanceSearch.metaSearch().session();
        String tagValue = resonanceSearch.metaSearch().tag();

        FilterExpressionTextParser parser = new FilterExpressionTextParser();
        var parsed = parser.parse("source == '" + sourceValue + "' || session == '" + sessionValue + "' || tag == '" + tagValue + "'");

        SearchRequest searchRequest =
                SearchRequest
                        .builder()
                        .topK(4)
                        .filterExpression(parsed)
                        .query(resonanceSearch.textSearch())
                        .build();

        return vectorStore.similaritySearch(searchRequest);
    }
}
