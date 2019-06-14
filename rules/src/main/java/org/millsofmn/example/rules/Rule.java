package org.millsofmn.example.rules;

import org.millsofmn.example.rules.action.Action;
import org.millsofmn.example.rules.action.SampleAction;
import org.millsofmn.example.rules.action.SampleFormAction;
import org.millsofmn.example.rules.criteria.Criteria;
import org.millsofmn.example.rules.form.SampleForm;
import org.millsofmn.example.rules.sample.Bin;
import org.millsofmn.example.rules.sample.Sample;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;

public class Rule {

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


    public boolean evaluate(SampleForm form) {
        boolean formNotValid = false;
        for (Sample sample : form.getSamples()) {
            boolean matchSample = true;
            for (Criteria criterion : criteria) {
                if (!criterion.evaluate(sample)) {
                    matchSample = false;
                }
            }
            formNotValid = true;

            if(matchSample){
                execute(sample);
            }
        }

        if (formNotValid) {
            execute(form);
        }

        return formNotValid;
    }

    public void execute(Sample sample) {
        for (Action act : actions) {
            if (act.getClass().equals(SampleAction.class)) {
                act.execute(sample);
            }
        }
    }

    public void execute(SampleForm form) {
        for (Action act : actions) {
            if (act.getClass().equals(SampleFormAction.class)) {
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
