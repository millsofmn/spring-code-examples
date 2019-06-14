package org.millsofmn.example.rules;

import org.millsofmn.example.rules.action.Action;
import org.millsofmn.example.rules.criteria.Criteria;
import org.millsofmn.example.rules.sample.Sample;

public class SampleRule extends Rule<Sample> {

    public boolean evaluate(Sample sample){
        for(Criteria criterion : criteria){
            if(!criterion.evaluate(sample)){
                return false;
            }
        }
        return true;
    }
    public void execute(Sample sample) {
        for(Action act : actions){
            act.execute(sample);
        }
    }
}
