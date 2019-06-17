package org.millsofmn.example.rules.rule;

import org.millsofmn.example.rules.action.Action;
import org.millsofmn.example.rules.action.FormAction;
import org.millsofmn.example.rules.criteria.Criteria;
import org.millsofmn.example.rules.sample.Form;
import org.millsofmn.example.rules.sample.Bin;
import org.millsofmn.example.rules.sample.Sample;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;

public abstract class Rule {

    private String description;
    private Bin bin;
    private int priority;

    protected List<Criteria> criteria = new ArrayList<>();
    protected List<Action> actions = new ArrayList<>();

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


    public abstract boolean evaluate(Form form);

    public void execute(Sample sample) {
        for (Action act : actions) {
            act.execute(sample);
        }
    }

    public void execute(Form form) {
        for (Action act : actions) {
            if (act.getClass().equals(FormAction.class)) {
                act.execute(form);
            }
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Rule.class.getSimpleName() + "[", "]")
                .add("description='" + description + "'")
                .add("bin=" + bin)
                .add("priority=" + priority)
                .add("criteria=" + criteria)
                .add("actions=" + actions)
                .toString();
    }

    public static class RuleComparator implements Comparator<Rule> {

        @Override
        public int compare(Rule r1, Rule r2) {
            int comparePriority = Integer.compare(r1.getPriority(), r2.getPriority());

            if (comparePriority == 0) {
                return Integer.compare(
                        r1.getBin().ordinal(),
                        r2.getBin().ordinal());
            }
            return comparePriority;
        }
    }
}
