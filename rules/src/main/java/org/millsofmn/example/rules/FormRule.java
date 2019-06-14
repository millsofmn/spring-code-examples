package org.millsofmn.example.rules;

import org.millsofmn.example.rules.criteria.Criteria;
import org.millsofmn.example.rules.form.SampleForm;
import org.millsofmn.example.rules.sample.Sample;

public class FormRule extends Rule {


    public boolean evaluate(SampleForm form) {
        boolean formNotValid = false;
        for (Criteria criterion : criteria) {
            for (Sample sample : form.getSamples()) {
                if (criterion.evaluate(sample)) {
                    execute(sample);
                }
            }
            formNotValid = true;
        }

        if (formNotValid) {
            execute(form);
        }

        return formNotValid;
    }
}
