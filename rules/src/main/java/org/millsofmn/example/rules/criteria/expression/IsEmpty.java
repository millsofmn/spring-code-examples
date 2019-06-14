package org.millsofmn.example.rules.criteria.expression;

import org.millsofmn.example.rules.criteria.expression.Expression;

public class IsEmpty implements Expression<String> {
    @Override
    public boolean evaluate(String value) {
        return value.isEmpty();
    }
}
