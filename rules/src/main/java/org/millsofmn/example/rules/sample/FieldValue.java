package org.millsofmn.example.rules.sample;

import java.util.ArrayList;
import java.util.List;

public class FieldValue {

    private String columnName = "";

    private String cellValue = "";

    private boolean conjugated = false;

    private ValidationState evalResult = ValidationState.NOT_VALIDATED;

    private List<String> messages = new ArrayList<>();


    public FieldValue(String columnName) {
        this.columnName = columnName;
    }

    public FieldValue(String columnName, String cellValue) {
        this.columnName = columnName;
        this.cellValue = cellValue;
    }

    public FieldValue(String columnName, String cellValue, boolean conjugated) {
        this.columnName = columnName;
        this.cellValue = cellValue;
        this.conjugated = conjugated;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public FieldValue columnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    public String getCellValue() {
        return cellValue;
    }

    public void setCellValue(String cellValue) {
        this.cellValue = cellValue;
    }

    public FieldValue cellValue(String cellValue) {
        this.cellValue = cellValue;
        return this;
    }

    /**
     * Concatenates all the messages into a string.
     *
     * @return string of all the messages.
     */
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        for(String m : messages){
            if(!m.isEmpty()){
                sb.append(" * ").append(m).append("\n");
            }
        }

        return sb.toString();
    }

    public ValidationState getEvalResult() {
        return evalResult;
    }

    public void setEvalResult(ValidationState evalResult) {
        this.evalResult = evalResult;
    }

    public FieldValue evalResult(ValidationState evalResult) {
        this.evalResult = evalResult;
        return this;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void addMessage(String message){
        this.messages.add(message);
    }

    public boolean isConjugated() {
        return conjugated;
    }

    public void setConjugated(boolean conjugated) {
        this.conjugated = conjugated;
    }

    @Override
    public String toString() {
        return  "FieldValue{" + "columnName='" + columnName + '\'' +
                ", cellValue='" + cellValue + '\'' +
                ", message='" + messages + '\'' +
                ", evalResult='" + evalResult + '\'' +
                '}';
    }
}
