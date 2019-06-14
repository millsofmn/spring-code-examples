package org.millsofmn.example.rules;

import org.millsofmn.example.rules.action.Action;
import org.millsofmn.example.rules.criteria.Criteria;
import org.millsofmn.example.rules.sample.Bin;

import java.util.ArrayList;
import java.util.List;

public class RuleBuilder<T> {

    String description = "";
    int priority = 10;
    Bin bin = Bin.FOURTH;

    List<Criteria> criteria = new ArrayList<>();
    List<Action> actions = new ArrayList<>();

    public RuleBuilder description(String description){
        this.description = description;
        return this;
    }

    public RuleBuilder priority(int priority){
        this.priority = priority;
        return this;
    }

    public RuleBuilder bin(Bin bin){
        this.bin = bin;
        return this;
    }

    public RuleBuilder when(Criteria criteria){
        this.criteria.add(criteria);

        return this;
    }

    public RuleBuilder and(Criteria criteria){
        this.criteria.add(criteria);

        return this;
    }

    public RuleBuilder then(Action action){
        this.actions.add(action);
        return this;
    }

    public RuleBuilder and(Action action){
        this.actions.add(action);
        return this;
    }

    public Rule buildRule(){
        Rule formRule = new Rule<T>();
        formRule.setCriteria(criteria);
        formRule.setActions(actions);
        formRule.setDescription(description);
        formRule.setPriority(priority);
        formRule.setBin(bin);

        return formRule;
    }
}
