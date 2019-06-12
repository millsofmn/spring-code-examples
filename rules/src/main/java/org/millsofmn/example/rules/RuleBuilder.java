package org.millsofmn.example.rules;

import org.millsofmn.example.rules.action.Action;
import org.millsofmn.example.rules.criteria.RuleCriteria;

import java.util.ArrayList;
import java.util.List;

public class RuleBuilder {

    String description = "";
    List<RuleCriteria> criteria = new ArrayList<>();
    List<Action> actions = new ArrayList<>();

    public RuleBuilder description(String description){
        this.description = description;
        return this;
    }

    public RuleBuilder when(RuleCriteria ruleCriteria){
        this.criteria.add(ruleCriteria);

        return this;
    }

    public RuleBuilder and(RuleCriteria ruleCriteria){
        this.criteria.add(ruleCriteria);

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

    public Rule build(){
        Rule rule = new Rule();
        rule.setCriteria(criteria);
        rule.setActions(actions);
        rule.setDescription(description);

        return rule;
    }
}
