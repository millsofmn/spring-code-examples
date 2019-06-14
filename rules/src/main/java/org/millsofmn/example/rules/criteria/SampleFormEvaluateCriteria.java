package org.millsofmn.example.rules.criteria;

import org.millsofmn.example.rules.criteria.expression.AreNotUnique;
import org.millsofmn.example.rules.criteria.expression.Expression;
import org.millsofmn.example.rules.criteria.expression.IsUnique;
import org.millsofmn.example.rules.sample.Sample;

public class SampleFormEvaluateCriteria implements Criteria<Sample> {

    private String columnName;
    private Expression expression;

    public static SampleFormEvaluateCriteria sampleFormColumn(String columnName) {
        return new SampleFormEvaluateCriteria(columnName);
    }

    public SampleFormEvaluateCriteria(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public boolean evaluate(Sample sample) {
        String value = sample.getFieldValue(columnName).getCellValue();
        if(value == null){
            value = "";
        }
        System.out.println("eval " + value);
        return expression.evaluate(value);
    }

    public SampleFormEvaluateCriteria isNotUnique(){
        expression = new AreNotUnique();
        return this;
    }

    public SampleFormEvaluateCriteria isUnique(){
        expression = new IsUnique();
        return this;
    }
}
