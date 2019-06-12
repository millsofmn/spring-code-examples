package org.millsofmn.example.rules;

import org.millsofmn.example.rules.sample.Sample;

import java.util.ArrayList;
import java.util.List;

public class EvaluatorEngine {

    List<SampleRule> sampleRules = new ArrayList<>();

    public void setSampleRules(List<SampleRule> sampleRules) {
        this.sampleRules = sampleRules;
    }

    public void run(Sample sample) {

        sampleRules.sort(new SampleRule.RuleComparator());

        for (SampleRule sampleRule : sampleRules) {
            System.out.println(sampleRule.getPriority() + " " + sampleRule.getBin() + " " + sampleRule.getDescription());
            if (sampleRule.evaluate(sample)) {
                sampleRule.execute(sample);
            }
        }
    }

    public void registerRule(SampleRule sampleRule) {
        sampleRules.add(sampleRule);
    }
}
