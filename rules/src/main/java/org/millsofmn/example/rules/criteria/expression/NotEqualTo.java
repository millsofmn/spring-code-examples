package org.millsofmn.example.rules.criteria.expression;

public class NotEqualTo extends EqualTo {

    public NotEqualTo(String match) {
        super(match);
    }

    @Override
    public boolean evaluate(String value) {
        return !super.evaluate(value);
    }
}
