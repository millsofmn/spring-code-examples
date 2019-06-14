package org.millsofmn.example.rules.criteria.sample;

import org.millsofmn.example.rules.criteria.Expression;

public class IsEmpty implements Expression<String> {
    @Override
    public boolean evaluate(String value) {
        return value.isEmpty();
    }
}
