package org.millsofmn.example.rules;

import org.millsofmn.example.rules.action.Action;
import org.millsofmn.example.rules.criteria.Criteria;
import org.millsofmn.example.rules.sample.Bin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;

public class Rule<T> {

    private String description;
    private Class<T> type;
    private Bin bin;
    private int priority;

    protected List<Criteria> criteria = new ArrayList<>();
    protected List<Action> actions = new ArrayList<>();

    public Rule(Class<T> type) {
        this.type = type;
    }

    public Class<T> getMyType() {
        return this.type;
    }

    public Bin getBin() {
        return bin;
    }

    public void setBin(Bin bin) {
        this.bin = bin;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCriteria(List<Criteria> criteria) {
        this.criteria = criteria;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }


    public boolean evaluate(T sample){
        for(Criteria criterion : criteria){
            if(!criterion.evaluate(sample)){
                return false;
            }
        }
        return true;
    }
    public void execute(T object) {
        for(Action act : actions){
            act.execute(object);
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SampleRule.class.getSimpleName() + "[", "]")
                .add("description='" + description + "'")
                .add("bin=" + bin)
                .add("priority=" + priority)
                .toString();
    }

    public static class RuleComparator implements Comparator<Rule> {

        @Override
        public int compare(Rule r1, Rule r2) {
            int comparePriority = Integer.compare(r1.getPriority(), r2.getPriority());

            if(comparePriority == 0){
                return Integer.compare(
                        r1.getBin().ordinal(),
                        r2.getBin().ordinal());
            }
            return comparePriority;
        }
    }
}
