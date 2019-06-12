package org.millsofmn.example.rules;

import org.millsofmn.example.rules.action.Action;
import org.millsofmn.example.rules.criteria.Criteria;
import org.millsofmn.example.rules.sample.Bin;

import java.util.ArrayList;
import java.util.List;

public class SampleRuleBuilder {

    String description = "";
    int priority = 10;
    Bin bin = Bin.FOURTH;

    List<Criteria> criteria = new ArrayList<>();
    List<Action> actions = new ArrayList<>();

    public SampleRuleBuilder description(String description){
        this.description = description;
        return this;
    }

    public SampleRuleBuilder priority(int priority){
        this.priority = priority;
        return this;
    }

    public SampleRuleBuilder bin(Bin bin){
        this.bin = bin;
        return this;
    }

    public SampleRuleBuilder when(Criteria criteria){
        this.criteria.add(criteria);

        return this;
    }

    public SampleRuleBuilder and(Criteria criteria){
        this.criteria.add(criteria);

        return this;
    }

    public SampleRuleBuilder then(Action action){
        this.actions.add(action);
        return this;
    }

    public SampleRuleBuilder and(Action action){
        this.actions.add(action);
        return this;
    }

    public SampleRule build(){
        SampleRule sampleRule = new SampleRule();
        sampleRule.setCriteria(criteria);
        sampleRule.setActions(actions);
        sampleRule.setDescription(description);
        sampleRule.setPriority(priority);
        sampleRule.setBin(bin);

        return sampleRule;
    }
}
