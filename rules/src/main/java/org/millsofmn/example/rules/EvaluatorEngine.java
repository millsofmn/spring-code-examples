package org.millsofmn.example.rules;

import java.util.ArrayList;
import java.util.List;

public class EvaluatorEngine {

    List<Rule> rules = new ArrayList<>();

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public void run(Sample sample) {

        for (Rule rule : rules) {
            if (rule.evaluate(sample)) {
                rule.execute(sample);
            }
        }
    }

    public void registerRule(Rule rule) {
        rules.add(rule);
    }
}
