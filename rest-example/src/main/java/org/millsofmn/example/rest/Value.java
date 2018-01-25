package org.millsofmn.example.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Value {
    private Long id;
    private String quote;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Value{");
        sb.append("id=").append(id);
        sb.append(", quote='").append(quote).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
