package org.millsofmn.example.rules;


import org.millsofmn.example.rules.action.Action;
import org.millsofmn.example.rules.criteria.Criteria;
import org.millsofmn.example.rules.sample.Bin;
import org.millsofmn.example.rules.sample.Sample;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;

public class SampleRule {

    private String description;

    private Bin bin;
    private int priority;

    private List<Criteria> criteria = new ArrayList<>();
    private List<Action> actions = new ArrayList<>();

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

    public boolean evaluate(Sample sample){

        for(Criteria criteria : this.criteria){
            if(!criteria.evaluate(sample)){
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SampleRule.class.getSimpleName() + "[", "]")
                .add("description='" + description + "'")
                .add("bin=" + bin)
                .add("priority=" + priority)
                .toString();
    }

    public void execute(Sample sample) {
        for(Action act : actions){
            act.execute(sample);
        }
    }

    public static class RuleComparator implements Comparator<SampleRule> {

        @Override
        public int compare(SampleRule r1, SampleRule r2) {
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
