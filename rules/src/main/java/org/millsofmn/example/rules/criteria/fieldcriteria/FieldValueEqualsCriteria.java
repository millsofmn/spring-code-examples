package org.millsofmn.example.rules.criteria.fieldcriteria;

import org.millsofmn.example.rules.FieldValue;
import org.millsofmn.example.rules.Sample;
import org.millsofmn.example.rules.criteria.RuleCriteria;

import java.util.ArrayList;
import java.util.List;

public class FieldValueEqualsCriteria implements RuleCriteria<Sample> {

    private String fieldName;
    private List<String> values = new ArrayList<>();

    public static FieldValueEqualsCriteria fieldValueIsNotEqualTo(String fieldName){
        return new FieldValueEqualsCriteria(fieldName);
    }

    public static FieldValueEqualsCriteria fieldValueNotFoundInList(String fieldName){
        return new FieldValueEqualsCriteria(fieldName);
    }

    public FieldValueEqualsCriteria(String fieldName) {
        this.fieldName = fieldName;
    }

    public FieldValueEqualsCriteria value(String value) {
        this.values.add(value);
        return this;
    }

    public FieldValueEqualsCriteria values(List<String> values) {
        this.values = values;
        return this;
    }

    @Override
    public boolean evaluate(Sample sample) {
        return  !values.contains(sample.getFieldValue(fieldName).getCellValue());
    }
}
