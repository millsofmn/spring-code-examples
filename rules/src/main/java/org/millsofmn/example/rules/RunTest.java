package org.millsofmn.example.rules;

import org.millsofmn.example.rules.sample.Bin;
import org.millsofmn.example.rules.sample.Priority;
import org.millsofmn.example.rules.sample.Sample;

import java.util.Arrays;

import static org.millsofmn.example.rules.action.SetFieldValueAction.setFieldValueColumn;
import static org.millsofmn.example.rules.criteria.SampleColumnCriteria.thisSampleColumn;

public class RunTest {
    public static void main(String[] args) {

        Sample sample = new Sample();
        sample.getId().setCellValue("abc");
        sample.getProject().setCellValue("PROJ");
        sample.getPanel().setCellValue("PANEL1 PANEL2");

        System.out.println(sample);

        EvaluatorEngine evaluator = new EvaluatorEngine();

        evaluator.registerRule(defaultRule());
        evaluator.registerRule(requiredRule());
        evaluator.registerRule(regexRule());
        evaluator.registerRule(equalsRule());

        evaluator.run(sample);
        System.out.println(sample);
    }

    public static SampleRule regexRule() {
        return new SampleRuleBuilder()
                .description("Project rule to validate regex")
                .priority(Priority.PROJECT_RULE.ordinal())
                .bin(Bin.SECOND)
                .when(thisSampleColumn(Sample.PROJECT).isEqualTo("PROJ"))
                .and(thisSampleColumn(Sample.ID).doesNotMatchRegex("123"))
                .then(setFieldValueColumn(Sample.ID).asInvalid().withMessage("Values Must Match"))
                .build();
    }

    public static SampleRule defaultRule() {
        return new SampleRuleBuilder()
                .description("Global rule to set gender to default hello kitty")
                .priority(Priority.GLOBAL_RULE.ordinal())
                .bin(Bin.FIRST)
                .when(thisSampleColumn(Sample.GENDER).isEmpty())
                .then(setFieldValueColumn(Sample.GENDER).withValue("Hello Kitty"))
                .build();

    }

    public static SampleRule requiredRule() {
        return new SampleRuleBuilder()
                .description("Panel rule Require that DNA source for panel 'PANEL'")
                .priority(Priority.PANEL_RULE.ordinal())
                .bin(Bin.FOURTH)
                .when(thisSampleColumn(Sample.PANEL).isSplitOnSpace().isEqualTo("PANEL1"))
                .and(thisSampleColumn(Sample.DNA_SOURCE).isEmpty())
                .then(setFieldValueColumn(Sample.DNA_SOURCE).withMessage("DNA source is required for panel"))
                .build();
    }

    public static SampleRule equalsRule() {
        return new SampleRuleBuilder()
                .description("Panel rule comment is in list'")
                .priority(Priority.PANEL_RULE.ordinal())
                .bin(Bin.THIRD)
                .when(thisSampleColumn(Sample.PANEL).isSplitOnSpace().isEqualTo("PANEL2"))
                .and(thisSampleColumn(Sample.COMMENTS).isNotFoundInList(Arrays.asList("Test", "Tester")))
                .then(setFieldValueColumn(Sample.COMMENTS).asInvalid().withMessage("Does not contain tester."))
                .build();
    }
}