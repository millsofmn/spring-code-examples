package org.millsofmn.example.rules;

import org.millsofmn.example.rules.form.SampleForm;
import org.millsofmn.example.rules.sample.Bin;
import org.millsofmn.example.rules.sample.Priority;
import org.millsofmn.example.rules.sample.Sample;

import java.util.ArrayList;
import java.util.List;

import static org.millsofmn.example.rules.action.SampleFieldValueAction.setFieldValueColumn;
import static org.millsofmn.example.rules.criteria.SampleCriteria.thisSampleColumn;
import static org.millsofmn.example.rules.criteria.SampleFormCriteria.theseSampleFormColumns;


public class FormRuleTest {

    public static void main(String[] args) {

        SampleForm form = new SampleForm();
        form.setSamples(createSamples());

        Rule rule1 = createPlateIdRule();
        Rule rule2 = createPlateIdNotEmptyRule();

        if(rule1.evaluate(form)){
            System.out.println("+++++++++");
        } else {
            System.out.println("0ifsd");
        }

        if(rule1.evaluate(form)){
            System.out.println("+++++++++");
        } else {
            System.out.println("0ifsd");
        }
    }

    private static List<Sample> createSamples(){
        List<Sample> samples = new ArrayList<>();

        Sample sample1 = new Sample();
        sample1.getId().setCellValue("abc");
        sample1.getPlateId().setCellValue("");
        sample1.getProject().setCellValue("PROJ");
        sample1.getPanel().setCellValue("PANEL1 PANEL2");
        samples.add(sample1);

        Sample sample2 = new Sample();
        sample2.getId().setCellValue("efg");
        sample2.getPlateId().setCellValue("");
        sample2.getProject().setCellValue("PROJ");
        sample2.getPanel().setCellValue("PANEL1 PANEL2");
        samples.add(sample2);

        return samples;
    }

    private static Rule createPlateIdRule(){

        return new RuleBuilder()
                .description("Form Rule to have same plate id")
                .when(theseSampleFormColumns(Sample.PLATE_ID).areNotEqual())
//                .then(setFieldValueColumn("").asInvalid().withMessage("Plate Id must be same"))
                .buildRule();
    }

    public static Rule createPlateIdNotEmptyRule() {
        return new RuleBuilder()
                .description("Panel rule Require that DNA source for panel 'PANEL'")
                .priority(Priority.PANEL_RULE.ordinal())
                .bin(Bin.FOURTH)
                .when(thisSampleColumn(Sample.PLATE_ID).isNotEmpty())
//                .then(setFieldValueColumn(Sample.PLATE_ID).withMessage("DNA source is required for panel"))
                .buildRule();
    }
}
