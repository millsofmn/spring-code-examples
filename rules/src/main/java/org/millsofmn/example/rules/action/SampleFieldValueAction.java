package org.millsofmn.example.rules.action;

import org.millsofmn.example.rules.sample.FieldValue;
import org.millsofmn.example.rules.sample.Sample;
import org.millsofmn.example.rules.sample.ValidationState;

public class SampleFieldValueAction implements Action<Sample> {

    private String columnName;
    private String message;
    private ValidationState validationState;
    private String value;

    public static SampleFieldValueAction setFieldValueColumn(String columnName) {
        return new SampleFieldValueAction(columnName);
    }

    private SampleFieldValueAction(String columnName) {
        this.columnName = columnName;
    }

    public SampleFieldValueAction asInvalid(){
        this.validationState = ValidationState.INVALID;
        return this;
    }

    public SampleFieldValueAction asValid(){
        this.validationState = ValidationState.VALID;
        return this;
    }

    public SampleFieldValueAction asDefaulted(){
        this.validationState = ValidationState.DEFAULT_VALUE;
        return this;
    }

    public SampleFieldValueAction asIgnored(){
        this.validationState = ValidationState.IGNORED;
        return this;
    }

    public SampleFieldValueAction asNotValidated(){
        this.validationState = ValidationState.NOT_VALIDATED;
        return this;
    }

    public SampleFieldValueAction withMessage(String message){
        this.message = message;
        return this;
    }

    public SampleFieldValueAction withValue(String value){
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
            fieldValue.setMessage(message);
        }
        if(value != null){
            fieldValue.setCellValue(value);
        }
    }
}
