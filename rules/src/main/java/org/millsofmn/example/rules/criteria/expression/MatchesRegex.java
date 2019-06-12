package org.millsofmn.example.rules.criteria.expression;

public class MatchesRegex implements Expression<String> {

    private String regex;

    public MatchesRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public boolean evaluate(String value) {
        return value.matches(regex);
    }
}
