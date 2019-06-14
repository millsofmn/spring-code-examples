package org.millsofmn.example.rules;

import org.millsofmn.example.rules.form.SampleForm;
import org.millsofmn.example.rules.sample.Bin;
import org.millsofmn.example.rules.sample.Priority;
import org.millsofmn.example.rules.sample.Sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.millsofmn.example.rules.action.SampleAction.setFieldValueColumn;
import static org.millsofmn.example.rules.criteria.SampleFormEvaluateCriteria.sampleFormColumn;
import static org.millsofmn.example.rules.criteria.SampleCriteria.thisSampleColumn;


public class RuleTests {

    public static void main(String[] args) {

        SampleForm form = new SampleForm();
        form.setSamples(createSamples());

        EvaluatorEngine evaluator = new EvaluatorEngine();
        evaluator.registerRule(createPlateIdRule());
        evaluator.registerRule(createPlateIdNotEmptyRule());
        evaluator.registerRule(createSampleIdMustBeUniqueRule());

        evaluator.registerRule(defaultRule());
        evaluator.registerRule(requiredRule());
        evaluator.registerRule(regexRule());
        evaluator.registerRule(equalsRule());

        evaluator.run(form);

        System.out.println(form);
    }

    private static List<Sample> createSamples(){
        List<Sample> samples = new ArrayList<>();

        Sample sample = new Sample();
        sample.getId().setCellValue("123");
        sample.getPlateId().setCellValue("123ABC-De");
        sample.getProject().setCellValue("PROJ");
        sample.getPanel().setCellValue("PANEL1 PANEL2");
        samples.add(sample);

        Sample sample1 = new Sample();
        sample1.getId().setCellValue("SMPL01");
        sample1.getPlateId().setCellValue("123ABC-D");
        sample1.getProject().setCellValue("PROJ");
        sample1.getPanel().setCellValue("PANEL1 PANEL2");
        samples.add(sample1);

        Sample sample2 = new Sample();
        sample2.getId().setCellValue("SMPL02");
        sample2.getPlateId().setCellValue("");
        sample2.getProject().setCellValue("PROJ");
        sample2.getPanel().setCellValue("PANEL1");
        samples.add(sample2);

        Sample sample3 = new Sample();
        sample3.getId().setCellValue("SMPL02");
        sample3.getPlateId().setCellValue("123ABC-D");
        sample3.getProject().setCellValue("PROJ");
        sample3.getPanel().setCellValue("PANEL1");
        samples.add(sample3);

        return samples;
    }

    private static Rule createPlateIdRule(){

        return new RuleBuilder()
                .description("Form Rule to have same plate id")
                .when(sampleFormColumn(Sample.PLATE_ID).isUnique())
                .then(setFieldValueColumn(Sample.PLATE_ID).asInvalid().withMessage("Plate Id must be same."))
//                .and(setSampleForm().asInvalid().withMessage("Plate Ids do not match"))
                .buildRule();
    }

    public static Rule createPlateIdNotEmptyRule() {
        return new RuleBuilder()
                .description("Panel rule Require that DNA source for panel 'PANEL'")
                .priority(Priority.PANEL_RULE.ordinal())
                .bin(Bin.FOURTH)
                .when(thisSampleColumn(Sample.PLATE_ID).isEmpty())
                .then(setFieldValueColumn(Sample.PLATE_ID).asValid().withMessage("I'm not empty"))
                .buildRule();
    }

    public static Rule createSampleIdMustBeUniqueRule() {
        return new RuleBuilder()
                .description("Panel rule Require that DNA source for panel 'PANEL'")
                .priority(Priority.PANEL_RULE.ordinal())
                .bin(Bin.FOURTH)
                .when(sampleFormColumn(Sample.ID).isNotUnique())
                .then(setFieldValueColumn(Sample.ID).asValid().withMessage("I am a duplicate"))
                .buildRule();
    }

    public static Rule regexRule() {
        return new RuleBuilder()
                .description("Project rule to validate regex")
                .priority(Priority.PROJECT_RULE.ordinal())
                .bin(Bin.SECOND)
                .ruleType(RuleBuilder.RuleType.SAMPLE)
                .when(thisSampleColumn(Sample.PROJECT).isEqualTo("PROJ"))
                .and(thisSampleColumn(Sample.ID).matchesRegex("123"))
                .then(setFieldValueColumn(Sample.ID).asInvalid().withMessage("For sample id 123 with project PROJ"))
                .buildRule();
    }

    public static Rule defaultRule() {
        return new RuleBuilder()
                .description("Global rule to set gender to default hello kitty")
                .priority(Priority.GLOBAL_RULE.ordinal())
                .ruleType(RuleBuilder.RuleType.SAMPLE)
                .bin(Bin.FIRST)
                .when(thisSampleColumn(Sample.GENDER).isEmpty())
                .then(setFieldValueColumn(Sample.GENDER).withValue("Hello Kitty"))
                .buildRule();

    }

    public static Rule requiredRule() {
        return new RuleBuilder()
                .description("Panel rule Require that DNA source for panel 'PANEL'")
                .priority(Priority.PANEL_RULE.ordinal())
                .ruleType(RuleBuilder.RuleType.SAMPLE)
                .bin(Bin.FOURTH)
                .when(thisSampleColumn(Sample.PANEL).splitColumnValueOnSpace().isEqualTo("PANEL1"))
                .and(thisSampleColumn(Sample.DNA_SOURCE).isEmpty())
                .then(setFieldValueColumn(Sample.DNA_SOURCE).withMessage("DNA source is required for panel"))
                .buildRule();
    }

    public static Rule equalsRule() {
        return new RuleBuilder()
                .description("Panel rule comment is in list'")
                .ruleType(RuleBuilder.RuleType.SAMPLE)
                .priority(Priority.PANEL_RULE.ordinal())
                .bin(Bin.THIRD)
                .when(thisSampleColumn(Sample.PANEL).splitColumnValueOnSpace().isEqualTo("PANEL2"))
                .and(thisSampleColumn(Sample.COMMENTS).isNotFoundInList(Arrays.asList("Test", "Tester")))
                .then(setFieldValueColumn(Sample.COMMENTS).asInvalid().withMessage("Does not contain tester."))
                .buildRule();
    }
}
