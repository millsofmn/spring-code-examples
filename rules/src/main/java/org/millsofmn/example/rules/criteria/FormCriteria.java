package org.millsofmn.example.rules.criteria;

import org.millsofmn.example.rules.criteria.expression.IsNotUnique;
import org.millsofmn.example.rules.criteria.expression.Expression;
import org.millsofmn.example.rules.criteria.expression.IsUnique;
import org.millsofmn.example.rules.sample.Sample;

public class FormCriteria implements Criteria<Sample> {

    private String columnName;
    private Expression<String> expression;

    public static FormCriteria sampleFormColumn(String columnName) {
        return new FormCriteria(columnName);
    }

    private FormCriteria(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public boolean evaluate(Sample sample) {
        String value = sample.getFieldValue(columnName).getCellValue();
        if(value == null){
            value = "";
        }
        return expression.evaluate(value);
    }

    public FormCriteria isNotUnique(){
        expression = new IsNotUnique();
        return this;
    }

    public FormCriteria hasUniqueValues(){
        expression = new IsUnique();
        return this;
    }
}
