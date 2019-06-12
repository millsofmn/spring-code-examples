package org.millsofmn.example.rules.criteria;

import org.millsofmn.example.rules.sample.Sample;
import org.millsofmn.example.rules.criteria.expression.*;

import java.util.Arrays;
import java.util.List;

public class SampleColumnCriteria implements Criteria<Sample> {

    boolean splitColumnValues = false;
    String columnName;
    Expression<String> expression;

    public static SampleColumnCriteria thisSampleColumn(String columnName){
        return new SampleColumnCriteria(columnName);
    }

    private SampleColumnCriteria(String columnName) {
        this.columnName = columnName;
    }

    public SampleColumnCriteria isNotEqualTo(String value){
        expression = new NotEqualTo(value);
        return this;
    }

    public SampleColumnCriteria isSplitOnSpace(){
        splitColumnValues = true;
        return this;
    }

    public SampleColumnCriteria isEqualTo(String value){
        expression = new EqualTo(value);
        return this;
    }

    public SampleColumnCriteria isEmpty(){
        expression = new IsEmpty();
        return this;
    }

    public SampleColumnCriteria matchesRegex(String regex){
        expression = new MatchesRegex(regex);
        return this;
    }

    public SampleColumnCriteria doesNotMatchRegex(String regex){
        expression = new DoesNotMatchRegex(regex);
        return this;
    }

    public SampleColumnCriteria isNotFoundInList(List<String> items){
        expression = new NotFoundInList(items);
        return this;
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
}
