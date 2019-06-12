package org.millsofmn.example.rules.action;

import org.millsofmn.example.rules.sample.FieldValue;
import org.millsofmn.example.rules.sample.Sample;
import org.millsofmn.example.rules.sample.ValidationState;

public class SetFieldValueAction implements Action {

    private String columnName;
    private String message;
    private ValidationState validationState;
    private String value;

    public static SetFieldValueAction setFieldValueColumn(String columnName) {
        return new SetFieldValueAction(columnName);
    }

    private SetFieldValueAction(String columnName) {
        this.columnName = columnName;
    }

    public SetFieldValueAction asInvalid(){
        this.validationState = ValidationState.INVALID;
        return this;
    }

    public SetFieldValueAction asValid(){
        this.validationState = ValidationState.VALID;
        return this;
    }

    public SetFieldValueAction asDefaulted(){
        this.validationState = ValidationState.DEFAULT_VALUE;
        return this;
    }

    public SetFieldValueAction asIgnored(){
        this.validationState = ValidationState.IGNORED;
        return this;
    }

    public SetFieldValueAction asNotValidated(){
        this.validationState = ValidationState.NOT_VALIDATED;
        return this;
    }

    public SetFieldValueAction withMessage(String message){
        this.message = message;
        return this;
    }

    public SetFieldValueAction withValue(String value){
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
