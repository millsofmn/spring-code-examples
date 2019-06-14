package org.millsofmn.example.rules.criteria.form;

import org.millsofmn.example.rules.criteria.Expression;
import org.millsofmn.example.rules.form.SampleForm;

public class IsEmpty implements Expression<SampleForm> {

    private String fieldName;

    public IsEmpty(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public boolean evaluate(SampleForm data) {

        return data.getSamples()
                .stream()
                .anyMatch(s -> s.getFieldValue(fieldName).getCellValue().isEmpty());
    }
}
