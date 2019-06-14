package org.millsofmn.example.rules.criteria.form;

import org.millsofmn.example.rules.form.SampleForm;

public class IsNotEmpty extends IsEmpty {

    public IsNotEmpty(String fieldName) {
        super(fieldName);
    }

    @Override
    public boolean evaluate(SampleForm data) {
        return !super.evaluate(data);
    }
}
