package org.millsofmn.example.rules.criteria.fieldcriteria;

import org.millsofmn.example.rules.Sample;
import org.millsofmn.example.rules.criteria.RuleCriteria;

public class FieldValueEmptyCriteria implements RuleCriteria<Sample> {

    private String fieldName;

    public static FieldValueEmptyCriteria fieldValueIsEmpty(String fieldName){
        return new FieldValueEmptyCriteria(fieldName);
    }

    public FieldValueEmptyCriteria(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public boolean evaluate(Sample sample) {
        if(sample.getFieldValue(fieldName).getCellValue().isEmpty()){
            return true;
        } else {
            return false;
        }
    }
}
