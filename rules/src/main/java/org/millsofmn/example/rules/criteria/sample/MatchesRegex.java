package org.millsofmn.example.rules.criteria.sample;

import org.millsofmn.example.rules.criteria.Expression;

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
