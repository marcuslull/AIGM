package com.marcuslull.aigm.data.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcuslull.aigm.data.model.ResonanceSearch;
import com.marcuslull.aigm.router.ResponseRouter;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionTextParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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

    private ResponseRouter responseRouter;

    @Autowired // Lazy setter DI to avoid circular DI on startup
    public void setResponseRouter(@Lazy ResponseRouter responseRouter) {
        this.responseRouter = responseRouter;
    }

    public List<Document> query(ResonanceSearch query) {

        String sourceValue = query.metaSearch().source();
        String sessionValue = query.metaSearch().session();
        String tagValue = query.metaSearch().tag();

        FilterExpressionTextParser parser = new FilterExpressionTextParser();
        var parsed = parser.parse("source == '" + sourceValue + "' || session == '" + sessionValue + "' || tag == '" + tagValue + "'");

        SearchRequest searchRequest =
                SearchRequest
                        .builder()
                        .topK(4)
                        .filterExpression(parsed)
                        .query(query.textSearch())
                        .build();

        return vectorStore.similaritySearch(searchRequest);
    }

    public void handle(ResonanceSearch resonanceSearch) throws JsonProcessingException {

        List<Document> queryResponse = query(resonanceSearch);
        ResonanceSearch queryResponseResonanceSearch = resonanceSearch.copyWithResults(queryResponse);
//        AiResponse response = new AiResponse(null, queryResponseResonanceSearch, null);
//        String responseAsString = mapper.writeValueAsString(response);

//        responseRouter.route(responseAsString);
    }
}
