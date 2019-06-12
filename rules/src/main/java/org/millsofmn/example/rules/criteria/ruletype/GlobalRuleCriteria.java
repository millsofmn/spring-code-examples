package org.millsofmn.example.rules.criteria.ruletype;

import org.millsofmn.example.rules.FieldValue;
import org.millsofmn.example.rules.Sample;
import org.millsofmn.example.rules.criteria.RuleCriteria;

public class GlobalRuleCriteria implements RuleCriteria<Sample> {

    public static GlobalRuleCriteria itIsGlobalRule(){
        return new GlobalRuleCriteria();
    }

    @Override
    public boolean evaluate(Sample fv) {
        return true;
    }
}
