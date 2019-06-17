package org.millsofmn.example.rules.rule;

import org.millsofmn.example.rules.criteria.Criteria;
import org.millsofmn.example.rules.sample.Form;
import org.millsofmn.example.rules.sample.Sample;

public class SampleRule extends Rule {

    @Override
    public boolean evaluate(Form form) {
        boolean formNotValid = false;
        for (Sample sample : form.getSamples()) {

            // assume that it will match and if it doesn't then sa
            boolean matchSample = true;
            for (Criteria criterion : criteria) {
                if (!criterion.evaluate(sample)) {
                    matchSample = false;
                }
            }
            formNotValid = true;

            if (matchSample) {
                execute(sample);
            }
        }

        if (formNotValid) {
            execute(form);
        }

        return formNotValid;
    }
}
