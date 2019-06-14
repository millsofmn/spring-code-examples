package org.millsofmn.example.rules.criteria;

import org.millsofmn.example.rules.criteria.form.IsNotEqual;
import org.millsofmn.example.rules.criteria.form.IsNotEmpty;
import org.millsofmn.example.rules.form.SampleForm;

public class SampleFormCriteria implements Criteria<SampleForm> {

    private String columnName;
    private Expression expression;

    public static SampleFormCriteria theseSampleFormColumns(String columnName){
        return new SampleFormCriteria(columnName);
    }

    private SampleFormCriteria(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public boolean evaluate(SampleForm data) {
        System.out.println("manip");
        return expression.evaluate(data);

    }

    public SampleFormCriteria isNotEmpty(){
        expression = new IsNotEmpty(columnName);
        return this;
    }

    public SampleFormCriteria areNotEqual(){
        expression = new IsNotEqual(columnName);
        return this;
    }

    public SampleFormCriteria areNotEmpty() {
        expression = new IsNotEqual(columnName);
        return this;
    }
}
