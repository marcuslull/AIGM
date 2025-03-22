package com.marcuslull.aigm.data.model;

public record ResonanceSearch(MetaSearch metaSearch, String textSearch) {
    @Override
    public String toString() {
        return "ResonanceSearch{" +
                "metaSearch=" + metaSearch +
                ", textSearch='" + textSearch + '\'' +
                '}';
    }
}
