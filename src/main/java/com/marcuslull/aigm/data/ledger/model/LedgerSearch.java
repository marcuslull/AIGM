package com.marcuslull.aigm.data.ledger.model;

public class LedgerSearch {

    private String category;
    private String name;
    private String response;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "LedgerSearch{" +
                "category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", response='" + response + '\'' +
                '}';
    }
}
