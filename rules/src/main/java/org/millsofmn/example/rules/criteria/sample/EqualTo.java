package org.millsofmn.example.rules.criteria.sample;

import org.millsofmn.example.rules.criteria.Expression;

public class EqualTo implements Expression<String> {

    private String match;

    public EqualTo(String match) {
        this.match = match;
    }

    @Override
    public boolean evaluate(String value) {
        return match.equalsIgnoreCase(value);
    }
}
