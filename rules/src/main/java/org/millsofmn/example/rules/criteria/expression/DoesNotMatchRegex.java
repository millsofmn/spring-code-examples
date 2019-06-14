package org.millsofmn.example.rules.criteria.expression;

public class DoesNotMatchRegex extends MatchesRegex {

    public DoesNotMatchRegex(String regex) {
        super(regex);
    }

    @Override
    public boolean evaluate(String value) {
        return !super.evaluate(value);
    }
}
