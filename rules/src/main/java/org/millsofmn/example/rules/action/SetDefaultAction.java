package org.millsofmn.example.rules.action;

import org.millsofmn.example.rules.FieldValue;
import org.millsofmn.example.rules.Sample;
import org.millsofmn.example.rules.ValidationState;

public class SetDefaultAction implements Action {

    private String field;
    private String value;

    public static SetDefaultAction setFieldToDefaultValue(String defaultValue){
        return new SetDefaultAction(defaultValue);
    }

    public SetDefaultAction(String field) {
        this.field = field;
    }

    public SetDefaultAction value(String value) {
        this.value = value;
        return  this;
    }

    @Override
    public void execute(Sample sample) {
        FieldValue fieldValue = sample.getFieldValue(field);
        fieldValue.setEvalResult(ValidationState.DEFAULT_VALUE);
        fieldValue.setMessage("Default value set");
        fieldValue.setCellValue(value);
    }
}
