package org.millsofmn.example.rules.criteria.expression;

import java.util.HashSet;
import java.util.Set;

public class AreNotUnique implements Expression<String> {
    Set<String> uniqueValues = new HashSet<>();
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
