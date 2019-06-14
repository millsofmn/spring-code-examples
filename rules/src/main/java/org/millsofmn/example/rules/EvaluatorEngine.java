package org.millsofmn.example.rules;

import org.millsofmn.example.rules.form.SampleForm;
import org.millsofmn.example.rules.sample.Sample;

import java.util.ArrayList;
import java.util.List;

public class EvaluatorEngine {

    List<Rule> sampleRules = new ArrayList<>();

    public void run(Sample sample) {

        sampleRules.sort(new Rule.RuleComparator());

        for (Rule sampleRule : sampleRules) {
            System.out.println(sampleRule.getPriority() + " " + sampleRule.getBin() + " " + sampleRule.getDescription());
            if (sampleRule.evaluate(sample)) {
                sampleRule.execute(sample);
            }
        }
    }

    public void registerRule(Rule sampleRule) {
        sampleRules.add(sampleRule);
    }
}
