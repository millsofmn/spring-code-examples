package org.millsofmn.example.rules;

import org.millsofmn.example.rules.criteria.Criteria;
import org.millsofmn.example.rules.form.SampleForm;

public class FormRule extends Rule<SampleForm> {

    @Override
    public boolean evaluate(SampleForm sample) {
        for(Criteria criterion : criteria){
            if(!criterion.evaluate(sample)){
                return false;
            }
        }
        return true;
    }

    @Override
    public void execute(SampleForm sample) {

    }
}
