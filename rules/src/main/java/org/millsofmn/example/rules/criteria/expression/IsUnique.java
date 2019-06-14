package org.millsofmn.example.rules.criteria.expression;

public class IsUnique implements Expression<String> {

    String thisValue = "";

    @Override
    public boolean evaluate(String value) {
        if (thisValue.isEmpty()) {
            thisValue = value;
        }

        return !thisValue.equals(value);
    }
}
