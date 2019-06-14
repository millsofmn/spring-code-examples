package org.millsofmn.example.rules.criteria.sample;

public class DoesNotMatchRegex extends MatchesRegex {

    public DoesNotMatchRegex(String regex) {
        super(regex);
    }

    @Override
    public boolean evaluate(String value) {
        return !super.evaluate(value);
    }
}
