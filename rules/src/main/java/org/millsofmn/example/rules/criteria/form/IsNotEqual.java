package org.millsofmn.example.rules.criteria.form;

import org.millsofmn.example.rules.form.SampleForm;

public class IsNotEqual extends IsEqual {

    public IsNotEqual(String fieldName) {
        super(fieldName);
    }

    @Override
    public boolean evaluate(SampleForm value) {
        return !super.evaluate(value);
    }
}
