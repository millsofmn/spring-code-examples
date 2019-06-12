package org.millsofmn.example.rules.criteria.fieldcriteria;

import org.millsofmn.example.rules.Sample;
import org.millsofmn.example.rules.criteria.RuleCriteria;

public class FieldValueRegexCriteria implements RuleCriteria<Sample> {

    private String fieldName;
    private String regex;

    public static FieldValueRegexCriteria fieldValueDoesNotMatchRegex(String fieldName){
        return new FieldValueRegexCriteria(fieldName);
    }

    public FieldValueRegexCriteria(String fieldName) {
        this.fieldName = fieldName;
    }

    public FieldValueRegexCriteria regex(String regex) {
        this.regex = regex;
        return this;
    }

    @Override
    public boolean evaluate(Sample sample) {
        if(sample.getFieldValue(fieldName).getCellValue().matches(regex)){
            return false;
        } else {
            return true;
        }
    }
}
