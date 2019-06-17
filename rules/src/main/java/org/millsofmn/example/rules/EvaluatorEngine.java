package org.millsofmn.example.rules;

import org.millsofmn.example.rules.rule.Rule;
import org.millsofmn.example.rules.sample.Form;
import java.util.ArrayList;
import java.util.List;

public class EvaluatorEngine {

    List<Rule> sampleRules = new ArrayList<>();

    public void run(Form form) {

        sampleRules.sort(new Rule.RuleComparator());

        for (Rule sampleRule : sampleRules) {
            System.out.println(sampleRule.getPriority() + " " + sampleRule.getBin() + " " + sampleRule.getDescription());

            sampleRule.evaluate(form);
        }
    }

    public void registerRule(Rule sampleRule) {
        sampleRules.add(sampleRule);
    }
}
