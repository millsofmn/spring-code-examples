package org.millsofmn.example.rules.action;

import org.millsofmn.example.rules.sample.FieldValue;
import org.millsofmn.example.rules.sample.Sample;
import org.millsofmn.example.rules.sample.ValidationState;

public class SampleAction implements Action<Sample> {

    private String columnName;
    private String message;
    private ValidationState validationState;
    private String value;

    public static SampleAction setFieldValueColumn(String columnName) {
        return new SampleAction(columnName);
    }

    private SampleAction(String columnName) {
        this.columnName = columnName;
    }

    public SampleAction asInvalid(){
        this.validationState = ValidationState.INVALID;
        return this;
    }

    public SampleAction asValid(){
        this.validationState = ValidationState.VALID;
        return this;
    }

    public SampleAction asDefaulted(){
        this.validationState = ValidationState.DEFAULT_VALUE;
        return this;
    }

    public SampleAction asIgnored(){
        this.validationState = ValidationState.IGNORED;
        return this;
    }

    public SampleAction asNotValidated(){
        this.validationState = ValidationState.NOT_VALIDATED;
        return this;
    }

    public SampleAction withMessage(String message){
        this.message = message;
        return this;
    }

    public SampleAction withValue(String value){
        this.value = value;
        return this;
    }

    @Override
    public void execute(Sample sample) {
        FieldValue fieldValue = sample.getFieldValue(columnName);

        if(validationState != null) {
            fieldValue.setEvalResult(validationState);
        }
        if(message != null){
            fieldValue.addMessage(message);
        }
        if(value != null){
            fieldValue.setCellValue(value);
        }
    }
}
