package org.millsofmn.example.rules.criteria;

import org.millsofmn.example.rules.sample.Sample;
import org.millsofmn.example.rules.criteria.sample.*;

import java.util.Arrays;
import java.util.List;

public class SampleCriteria implements Criteria<Sample> {

    boolean splitColumnValues = false;
    String columnName;
    Expression<String> expression;

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
                System.out.println("Eval " + val);
                if(expression.evaluate(val)){
                    return true;
                }
            }
        } else {
            return expression.evaluate(sample.get(columnName));
        }
        return false;
    }

    public SampleCriteria isNotEqualTo(String value){
        expression = new NotEqualTo(value);
        return this;
    }

    public SampleCriteria splitColumnValueOnSpace(){
        splitColumnValues = true;
        return this;
    }

    public SampleCriteria isEqualTo(String value){
        expression = new EqualTo(value);
        return this;
    }

    public SampleCriteria isEmpty(){
        expression = new IsEmpty();
        return this;
    }

    public SampleCriteria matchesRegex(String regex){
        expression = new MatchesRegex(regex);
        return this;
    }

    public SampleCriteria doesNotMatchRegex(String regex){
        expression = new DoesNotMatchRegex(regex);
        return this;
    }

    public SampleCriteria isNotFoundInList(List<String> items){
        expression = new NotFoundInList(items);
        return this;
    }

    public Criteria isNotEmpty() {
        expression = new IsNotEmpty();
        return this;
    }
}
