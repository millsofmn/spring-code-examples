package org.millsofmn.example.rules.criteria.sample;


public class IsNotEmpty extends IsEmpty {

    @Override
    public boolean evaluate(String value) {
        return !super.evaluate(value);
    }
}
