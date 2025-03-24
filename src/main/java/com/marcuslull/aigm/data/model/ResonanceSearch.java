package com.marcuslull.aigm.data.model;

import org.springframework.ai.document.Document;

import java.util.List;

public record ResonanceSearch(MetaSearch metaSearch, String textSearch, List<Document> response) {
    @Override
    public String toString() {
        return "ResonanceSearch{" +
                "metaSearch=" + metaSearch +
                ", textSearch='" + textSearch + '\'' +
                ", response=" + response +
                '}';
    }

    public ResonanceSearch copyWithResults(List<Document> response) {
        return new ResonanceSearch(this.metaSearch, this.textSearch, response);
    }
}
