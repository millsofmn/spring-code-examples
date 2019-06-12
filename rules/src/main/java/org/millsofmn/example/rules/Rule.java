package org.millsofmn.example.rules;


import org.millsofmn.example.rules.action.Action;
import org.millsofmn.example.rules.criteria.RuleCriteria;

import java.util.ArrayList;
import java.util.List;

public class Rule {

    private String description;

    private List<RuleCriteria> criteria = new ArrayList<>();
    private List<Action> actions = new ArrayList<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCriteria(List<RuleCriteria> criteria) {
        this.criteria = criteria;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public boolean evaluate(Sample sample){

        for(RuleCriteria ruleCriteria : criteria){
            if(!ruleCriteria.evaluate(sample)){
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
