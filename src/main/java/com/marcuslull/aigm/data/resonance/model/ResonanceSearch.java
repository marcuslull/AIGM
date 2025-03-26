package com.marcuslull.aigm.data.resonance.model;


import java.util.List;

public class ResonanceSearch {
    private MetaSearch metaSearch;
    private String textSearch;
    private List<String> response;

    public ResonanceSearch() {
    }

    public ResonanceSearch(MetaSearch metaSearch, String textSearch, List<String> response) {
        this.metaSearch = metaSearch;
        this.textSearch = textSearch;
        this.response = response;
    }

    public MetaSearch getMetaSearch() {
        return metaSearch;
    }

    public void setMetaSearch(MetaSearch metaSearch) {
        this.metaSearch = metaSearch;
    }

    public String getTextSearch() {
        return textSearch;
    }

    public void setTextSearch(String textSearch) {
        this.textSearch = textSearch;
    }

    public List<String> getResponse() {
        return response;
    }

    public void setResponse(List<String> response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "ResonanceSearchClass{" +
                "metaSearch=" + metaSearch +
                ", textSearch='" + textSearch + '\'' +
                ", response=" + response +
                '}';
    }
}

