package org.millsofmn.example.rules;

import org.millsofmn.example.rules.action.Action;
import org.millsofmn.example.rules.criteria.Criteria;
import org.millsofmn.example.rules.criteria.FormCriteria;
import org.millsofmn.example.rules.criteria.SampleCriteria;
import org.millsofmn.example.rules.rule.FormRule;
import org.millsofmn.example.rules.rule.Rule;
import org.millsofmn.example.rules.rule.SampleRule;

import java.util.ArrayList;
import java.util.List;

public class RuleBuilder {

    private String description = "";
    private int priority = 10;
    private int bin = 4;

    private List<Criteria> criteria = new ArrayList<>();
    private List<Action> actions = new ArrayList<>();

    public RuleBuilder description(String description){
        this.description = description;
        return this;
    }

    public RuleBuilder priority(int priority){
        this.priority = priority;
        return this;
    }

    public RuleBuilder bin(int bin){
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
        Rule rule;
        if(!criteria.isEmpty() && criteria.get(0) instanceof SampleCriteria){
            rule = new SampleRule();
        } else if (!criteria.isEmpty() && criteria.get(0) instanceof FormCriteria){
            rule = new FormRule();
        } else {
            throw new UnsupportedOperationException();
        }
        
        rule.setCriteria(criteria);
        rule.setActions(actions);
        rule.setDescription(description);
        rule.setPriority(priority);
        rule.setBin(bin);

        return rule;
    }
}
