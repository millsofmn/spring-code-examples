package org.millsofmn.example.rules.criteria;

import org.millsofmn.example.rules.FieldValue;
import org.millsofmn.example.rules.Sample;

public class FieldNameCriteria implements RuleCriteria<Sample> {

    private String field;

    public static FieldNameCriteria itIsField(String field){
        return new FieldNameCriteria(field);
    }

    public FieldNameCriteria(String field) {
        this.field = field;
    }

    @Override
    public boolean evaluate(Sample sample) {
        if(sample.getFieldValue(field).getColumnName().equals(field)){
            System.out.println("Field is : " + field);
            return true;
        } else {
            return false;
        }
    }
}
