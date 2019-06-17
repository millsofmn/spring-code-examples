package org.millsofmn.example.rules.criteria;

import org.millsofmn.example.rules.criteria.expression.*;
import org.millsofmn.example.rules.sample.Sample;

import java.util.Arrays;
import java.util.List;

public class SampleCriteria implements Criteria<Sample> {

    private boolean splitColumnValues = false;
    private String columnName;
    private Expression<String> expression;

    public static SampleCriteria thisSampleColumn(String columnName){
        return new SampleCriteria(columnName);
    }

    private SampleCriteria(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public boolean evaluate(Sample sample) {
        if(splitColumnValues){
            List<String> values = Arrays.asList(sample.get(columnName).split("\\s+"));
            for(String val : values){
                if(expression.evaluate(val)){
                    return true;
                }
            }
        } else {
            return expression.evaluate(sample.get(columnName));
        }
        return false;
    }

    public SampleCriteria splitColumnValueOnSpace(){
        splitColumnValues = true;
        return this;
    }

    public SampleCriteria isEqualTo(String value){
        expression = input -> input.equalsIgnoreCase(value);
        return this;
    }

    public SampleCriteria isNotEqualTo(String value){
        expression = input -> !input.equalsIgnoreCase(value);
        return this;
    }

    public SampleCriteria isEmpty(){
        expression = input -> input == null || input.isEmpty();
        return this;
    }

    public Criteria isNotEmpty() {
        expression = input -> input != null || !input.isEmpty();
        return this;
    }

    public SampleCriteria matchesRegex(String regex){
        expression =  input -> input.matches(regex);
        return this;
    }

    public SampleCriteria doesNotMatchRegex(String regex){
        expression =  input -> !input.matches(regex);
        return this;
    }

    public SampleCriteria isNotFoundInList(List<String> items){
        expression = items::contains;
        return this;
    }
}
