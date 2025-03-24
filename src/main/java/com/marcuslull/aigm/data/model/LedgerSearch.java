package com.marcuslull.aigm.data.model;

import java.util.List;

public record LedgerSearch(String table, String where, String equalTo, List<String> response) {
    @Override
    public String toString() {
        return "LedgerSearch{" +
                "table='" + table + '\'' +
                ", where='" + where + '\'' +
                ", equalTo='" + equalTo + '\'' +
                ", response='" + response + '\'' +
                '}';
    }

    public LedgerSearch copyWithResults(List<String> response) {
        return new LedgerSearch(this.table, this.where, this.equalTo, response);
    }
}

