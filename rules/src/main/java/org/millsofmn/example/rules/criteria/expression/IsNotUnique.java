package org.millsofmn.example.rules.criteria.expression;

import java.util.HashSet;
import java.util.Set;

public class IsNotUnique implements Expression<String> {
    private Set<String> uniqueValues = new HashSet<>();

    @Override
    public boolean evaluate(String value) {

        if (uniqueValues.contains(value)) {
            return true;
        } else {
            uniqueValues.add(value);
            return false;
        }
    }
}
