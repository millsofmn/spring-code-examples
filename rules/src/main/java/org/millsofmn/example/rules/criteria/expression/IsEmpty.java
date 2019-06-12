package org.millsofmn.example.rules.criteria.expression;

public class IsEmpty implements Expression<String> {
    @Override
    public boolean evaluate(String value) {
        return value.isEmpty();
    }
}
