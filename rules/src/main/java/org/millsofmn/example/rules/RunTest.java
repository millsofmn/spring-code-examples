package org.millsofmn.example.rules;

import static org.millsofmn.example.rules.action.SetDefaultAction.setFieldToDefaultValue;
import static org.millsofmn.example.rules.action.SetErrorAction.setFieldInError;
import static org.millsofmn.example.rules.criteria.fieldcriteria.FieldValueEmptyCriteria.fieldValueIsEmpty;
import static org.millsofmn.example.rules.criteria.fieldcriteria.FieldValueEqualsCriteria.fieldValueIsNotEqualTo;
import static org.millsofmn.example.rules.criteria.fieldcriteria.FieldValueRegexCriteria.fieldValueDoesNotMatchRegex;
import static org.millsofmn.example.rules.criteria.ruletype.GlobalRuleCriteria.itIsGlobalRule;
import static org.millsofmn.example.rules.criteria.ruletype.PanelRuleCriteria.itIsPanelRuleForMnemonic;
import static org.millsofmn.example.rules.criteria.ruletype.ProjectRuleCriteria.itIsProjectRuleForProject;

public class RunTest {
    public static void main(String[] args) {

        Sample sample = new Sample();
        sample.getId().setCellValue("abc");
        sample.getProject().setCellValue("PROJ");
        sample.getPanel().setCellValue("PANEL");

        EvaluatorEngine evaluator = new EvaluatorEngine();

//        defaultRule();
//        requiredRule();
//        regexRule();
//        equalsRule();

        evaluator.registerRule(defaultRule());
        evaluator.registerRule(requiredRule());
        evaluator.registerRule(regexRule());
        evaluator.registerRule(equalsRule());

        evaluator.run(sample);
        System.out.println(sample);
    }

    public static Rule regexRule() {
        Rule rule = new RuleBuilder()
                .description("Project rule to validate regex")
                .when(itIsProjectRuleForProject("PROJ"))
                .and(fieldValueDoesNotMatchRegex(Sample.ID).regex("123"))
                .then(setFieldInError(Sample.ID).message("Must match"))
                .build();

//        Sample sample = new Sample();
//        sample.getId().setCellValue("abc");
//        sample.getProject().setCellValue("PROJ");
//
//        executeRule(rule, sample);
//        System.out.println(sample.getId());
        return rule;
    }

    public static Rule defaultRule() {
        Rule rule = new RuleBuilder()
                .description("Global rule to set gender to default hello kitty")
                .when(itIsGlobalRule())
                .and(fieldValueIsEmpty(Sample.GENDER))
                .then(setFieldToDefaultValue(Sample.GENDER).value("Hello Kitty"))
                .build();

//        Sample sample = new Sample();
//        executeRule(rule, sample);
        return rule;
    }

    public static Rule requiredRule() {
        Rule rule = new RuleBuilder()
                .description("Require that DNA source for panel 'PANEL'")
                .when(itIsPanelRuleForMnemonic("PANEL"))
                .and(fieldValueIsEmpty(Sample.DNA_SOURCE))
                .then(setFieldInError(Sample.DNA_SOURCE).message("DNA source is required for panel"))
                .build();

//        Sample sample = new Sample();
//        sample.getPanel().setCellValue("PANEL");

//        executeRule(rule, sample);
        return rule;
    }

    public static Rule equalsRule() {
        Rule rule = new RuleBuilder()
                .description("Require that DNA source for panel 'PANEL'")
                .when(itIsPanelRuleForMnemonic("PANEL"))
                .and(fieldValueIsNotEqualTo(Sample.COMMENTS).value("Test"))
                .then(setFieldInError(Sample.COMMENTS).message("Does not contain test."))
                .build();

//        Sample sample = new Sample();
//        sample.getPanel().setCellValue("PANEL");
//
//        executeRule(rule, sample);
        return rule;
    }

    public static void executeRule(Rule rule, Sample sample) {
        System.out.println("Running rule : " + rule.getDescription());
        System.out.println("Sample before : " + sample);

        if (rule.evaluate(sample)) {
            rule.execute(sample);
        }

        System.out.println("Sample after : " + sample);

    }
}
