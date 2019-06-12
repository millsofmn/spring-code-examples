package org.millsofmn.example.rules.criteria.expression;

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
