package org.millsofmn.example.rules;

import org.millsofmn.example.rules.rule.Rule;
import org.millsofmn.example.rules.sample.Form;
import org.millsofmn.example.rules.sample.Priority;
import org.millsofmn.example.rules.sample.Sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.millsofmn.example.rules.action.AlphabetizeCellAction.alphabetize;
import static org.millsofmn.example.rules.action.ApplyDefaultPanelAction.findDefaultPanelForProject;
import static org.millsofmn.example.rules.action.TransformCellAction.transformFieldValueColumn;
import static org.millsofmn.example.rules.action.SampleAction.setFieldValueColumn;
import static org.millsofmn.example.rules.criteria.FormCriteria.sampleFormColumn;
import static org.millsofmn.example.rules.criteria.SampleCriteria.thisSampleColumn;


public class RuleTests {

    public static void main(String[] args) {

        Form form = new Form();
        form.setSamples(createSamples());

        EvaluatorEngine evaluator = new EvaluatorEngine();
        evaluator.registerRule(createRulePlateIdsMustMatch());
        evaluator.registerRule(createRulePlateIdMustNotBeEmpty());
        evaluator.registerRule(createRuleSampleIdsMustBeUnique());

        evaluator.registerRule(createRuleEqualToAndMatchesRegex());
        evaluator.registerRule(createRuleDefaultEmpty());
        evaluator.registerRule(createRuleRequired());
        evaluator.registerRule(createRuleEqualsValueInList());

        evaluator.registerRule(createRuleToAlphabetizePanels());
        evaluator.registerRule(createRuleToUpperCaseGenders());
        evaluator.registerRule(createRuleToSetDefaultPanelForProject());

        evaluator.run(form);

        System.out.println(form);
    }

    public static List<Sample> createSamples(){
        List<Sample> samples = new ArrayList<>();

        Sample sample = new Sample();
        sample.getId().setCellValue("123");
        sample.getPlateId().setCellValue("123ABC-De");
        sample.getProject().setCellValue("PROJ");
        sample.getPanel().setCellValue("PANEL2 PANEL1");
        sample.getPhase().setCellValue("Clinical Verification");
        sample.getDnaSource().setCellValue("DNA");
        sample.getGender().setCellValue("Male");
        samples.add(sample);

        Sample sample1 = new Sample();
        sample1.getId().setCellValue("SMPL01");
        sample1.getPlateId().setCellValue("123ABC-D");
        sample1.getProject().setCellValue("PROJ");
        sample1.getPanel().setCellValue("PANEL1 PANEL2");
        sample1.getDnaSource().setCellValue("DNA");
        sample1.getPhase().setCellValue("Clinical Verification");
        samples.add(sample1);

        Sample sample2 = new Sample();
        sample2.getId().setCellValue("SMPL02");
        sample2.getPlateId().setCellValue("");
        sample2.getProject().setCellValue("PROJ");
        sample2.getPanel().setCellValue("PANEL1");
        sample2.getPhase().setCellValue("Clinical");
        sample2.getGender().setCellValue("Female");
        samples.add(sample2);

        Sample sample3 = new Sample();
        sample3.getId().setCellValue("SMPL02");
        sample3.getPlateId().setCellValue("123ABC-De");
        sample3.getProject().setCellValue("PROJ");
        sample3.getPanel().setCellValue("PANEL2");
        sample3.getPhase().setCellValue("Clinical");
        samples.add(sample3);

        Sample sample4 = new Sample();
        sample4.getId().setCellValue("SMPL02");
        sample4.getPlateId().setCellValue("123ABC-De");
        sample4.getProject().setCellValue("PROJ");
        sample4.getPhase().setCellValue("Clinical");
        samples.add(sample4);

        return samples;
    }

    public static Rule createRulePlateIdsMustMatch(){
        return new RuleBuilder()
                .description("Form Rule: all plate ids must match")
                .when(sampleFormColumn(Sample.PLATE_ID).hasUniqueValues())
                .then(setFieldValueColumn(Sample.PLATE_ID).asInvalid().withMessage("All of the plate ids must match."))
                .buildRule();
    }

    public static Rule createRulePlateIdMustNotBeEmpty() {
        return new RuleBuilder()
                .description("Sample Rule: plate id must not be empty")
                .priority(Priority.PANEL_RULE.ordinal())
                .bin(4)
                .when(thisSampleColumn(Sample.PLATE_ID).isEmpty())
                .then(setFieldValueColumn(Sample.PLATE_ID).asValid().withMessage("Plate id cannot be left empty."))
                .buildRule();
    }

    public static Rule createRuleSampleIdsMustBeUnique() {
        return new RuleBuilder()
                .description("Form Rule: all sample ids must be unique")
                .priority(Priority.PANEL_RULE.ordinal())
                .bin(4)
                .when(sampleFormColumn(Sample.ID).isNotUnique())
                .then(setFieldValueColumn(Sample.ID).asInvalid().withMessage("Sample ids must be unique."))
                .buildRule();
    }

    public static Rule createRuleEqualToAndMatchesRegex() {
        return new RuleBuilder()
                .description("Sample Rule: project is equal to PROJ and Id has 123")
                .priority(Priority.PROJECT_RULE.ordinal())
                .bin(2)
                .and(thisSampleColumn(Sample.ID).matchesRegex("123"))
                .when(thisSampleColumn(Sample.PROJECT).isEqualTo("PROJ"))
                .then(setFieldValueColumn(Sample.ID).asIgnored().withMessage("Sample project is PROJ and Id has 123"))
                .buildRule();
    }

    public static Rule createRuleDefaultEmpty() {
        return new RuleBuilder()
                .description("Sample Rule: if gender not set default to hello kitty")
                .priority(Priority.GLOBAL_RULE.ordinal())
                .bin(1)
                .when(thisSampleColumn(Sample.GENDER).isEmpty())
                .then(setFieldValueColumn(Sample.GENDER).asDefaulted().withValue("Kitty").withMessage("Default empty gender to Hello Kitty"))
                .buildRule();

    }

    public static Rule createRuleRequired() {
        return new RuleBuilder()
                .description("Sample Rule: require that DNA source is required for panel 'PANEL'")
                .priority(Priority.PANEL_RULE.ordinal())
                .bin(4)
                .when(thisSampleColumn(Sample.PANEL).splitColumnValueOnSpace().isEqualTo("PANEL1"))
                .and(thisSampleColumn(Sample.DNA_SOURCE).isEmpty())
                .then(setFieldValueColumn(Sample.DNA_SOURCE).withMessage("DNA source is required for panel 'PANEL1'"))
                .buildRule();
    }

    public static Rule createRuleEqualsValueInList() {
        return new RuleBuilder()
                .description("Panel rule comment is in list'")
                .priority(Priority.PANEL_RULE.ordinal())
                .bin(3)
                .when(thisSampleColumn(Sample.PANEL).splitColumnValueOnSpace().isEqualTo("PANEL2"))
                .and(thisSampleColumn(Sample.GENDER).isNotFoundInList(Arrays.asList("Male", "Female")))
                .then(setFieldValueColumn(Sample.COMMENTS).asInvalid().withMessage("Panel2 gender is not Male or Female."))
                .buildRule();
    }

    public static Rule createRuleToAlphabetizePanels() {
        return new RuleBuilder()
                .description("Sample Rule: panels in alphabetical order")
                .priority(Priority.PANEL_RULE.ordinal())
                .bin(3)
                .when(thisSampleColumn(Sample.PANEL).hasMultipleValues())
                .then(alphabetize(Sample.PANEL).and(Sample.PHASE))
                .and(setFieldValueColumn(Sample.PANEL).asDefaulted().withMessage("Alphabetized Panels"))
                .buildRule();
    }

    public static Rule createRuleToUpperCaseGenders(){
        return new RuleBuilder()
                .description("Sample Rule: uppercaseColumnValue gender")
                .when(thisSampleColumn(Sample.GENDER).isNotEmpty())
                .then(transformFieldValueColumn(Sample.GENDER).toUppercase())
                .buildRule();
    }

    public static Rule createRuleToSetDefaultPanelForProject(){
        return new RuleBuilder()
                .description("Sample Rule: set default panel when missing")
                .priority(Priority.SAMPLE.ordinal())
                .when(thisSampleColumn(Sample.PROJECT).isNotEmpty())
                .and(thisSampleColumn(Sample.PANEL).isEmpty())
                .then(findDefaultPanelForProject(Sample.PROJECT).andSetValueInColumn(Sample.PANEL)
                        .ifFound(setFieldValueColumn(Sample.PANEL)
                                .asDefaulted().withMessage("Default panel value set.")))
                .buildRule();
    }
}
