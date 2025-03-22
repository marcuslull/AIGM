package com.marcuslull.aigm.data.model;

public record MetaSearch(String source, String session, String tag) {
    @Override
    public String toString() {
        return "MetaSearch{" +
                "source='" + source + '\'' +
                ", session='" + session + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}
