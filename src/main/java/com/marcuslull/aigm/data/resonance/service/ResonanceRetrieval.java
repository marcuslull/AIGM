package com.marcuslull.aigm.data.resonance.service;

import com.marcuslull.aigm.data.resonance.model.ResonanceSearch;
import com.marcuslull.aigm.router.CommunicationSender;
import com.marcuslull.aigm.router.model.CommunicationPacket;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionTextParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResonanceRetrieval {

    private final VectorStore vectorStore;
    private final CommunicationSender communicationSender;

    @Autowired
    public ResonanceRetrieval(VectorStore vectorStore, CommunicationSender communicationSender) {
        this.vectorStore = vectorStore;
        this.communicationSender = communicationSender;
    }

    public void process(CommunicationPacket communicationPacket) {

        // retrieve results
        List<Document> queryResponse = query(communicationPacket.getResonanceSearch());
        // convert to string because Spring is not able to deserialize them from Document some reason
        List<String> queryResponseString = queryResponse.stream().map(Document::getText).toList();

        // add in results, they should be sent back with the original query.
        communicationPacket.getResonanceSearch().setResponse(queryResponseString);
        System.out.println("RESONANCE SEARCH - " + communicationPacket.getAuthor() + ": " + communicationPacket);

        communicationSender.send(communicationPacket);
    }

    private List<Document> query(ResonanceSearch query) {

        String sourceValue = query.getMetaSearch().getSource();
        String sessionValue = query.getMetaSearch().getSession();
        String tagValue = query.getMetaSearch().getTag();

        FilterExpressionTextParser parser = new FilterExpressionTextParser();

        Filter.Expression parsed;
        if (sourceValue == null && sessionValue == null && tagValue == null) parsed = null;
        else { parsed = parser.parse("source == '" + sourceValue + "' || session == '" + sessionValue + "' || tag == '" + tagValue + "'"); }

        SearchRequest searchRequest =
                SearchRequest
                        .builder()
                        .topK(4)
                        .filterExpression(parsed)
                        .query(query.getTextSearch())
                        .build();

        return vectorStore.similaritySearch(searchRequest);
    }
}
