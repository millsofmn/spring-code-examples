package org.millsofmn.example.rules.rule;

import org.millsofmn.example.rules.criteria.Criteria;
import org.millsofmn.example.rules.sample.Form;
import org.millsofmn.example.rules.sample.Sample;

public class FormRule extends Rule {

    @Override
    public boolean evaluate(Form form) {
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
