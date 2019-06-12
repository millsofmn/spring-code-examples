package org.millsofmn.example.rules.action;

import org.millsofmn.example.rules.FieldValue;
import org.millsofmn.example.rules.Sample;
import org.millsofmn.example.rules.ValidationState;

public class SetErrorAction implements Action {

    private String field;
    private String message = "Value is required";

    public static SetErrorAction setFieldInError(String field){
        return new SetErrorAction(field);
    }

    public SetErrorAction(String field) {
        this.field = field;
    }

    public SetErrorAction message(String message){
        this.message = message;
        return this;
    }

    @Override
    public void execute(Sample sample) {
        FieldValue fieldValue = sample.getFieldValue(field);
        fieldValue.setEvalResult(ValidationState.INVALID);
        fieldValue.setMessage(message);
    }
}
