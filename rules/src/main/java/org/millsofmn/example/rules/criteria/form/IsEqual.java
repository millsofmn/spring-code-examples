package org.millsofmn.example.rules.criteria.form;

import org.millsofmn.example.rules.criteria.Expression;
import org.millsofmn.example.rules.form.SampleForm;

import java.util.List;
import java.util.stream.Collectors;

public class IsEqual implements Expression<SampleForm> {


    private String fieldName;

    public IsEqual(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public boolean evaluate(SampleForm value) {
        List<String> distinct = value.getSamples().stream()
                .map(sample -> sample.getFieldValue(fieldName).getCellValue())
                .distinct()
                .collect(Collectors.toList());

        if(distinct.isEmpty() || distinct.size() > 1){
            return false;
        } else {
            return true;
        }
    }
}
