package com.marcuslull.aigm.data.resonance.model;

public class MetaSearch {
    private String source;
    private String session;
    private String tag;

    public MetaSearch() {
    }

    public MetaSearch(String source, String session, String tag) {
        this.source = source;
        this.session = session;
        this.tag = tag;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "MetaSearch{" +
                "source='" + source + '\'' +
                ", session='" + session + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}
