package org.millsofmn.example.rules.sample;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Sample {

    // define the known columns
    public static final String ID = "ID";
    public static final String PANEL = "PANEL";
    public static final String PROJECT = "PROJECT";
    public static final String GENDER = "GENDER";
    public static final String DNA_SOURCE = "DNA_SOURCE";
    public static final String COMMENTS = "COMMENTS";

    /**
     * Map of all the fields
     */
    private Map<String, FieldValue> fieldValueMap = new HashMap<>();

    /**
     * default constructor that initializes the map object making up the sample
     */
    public Sample() {
        fieldValueMap.put(ID, new FieldValue(ID));
        fieldValueMap.put(PANEL, new FieldValue(PANEL));
        fieldValueMap.put(PROJECT, new FieldValue(PROJECT));
        fieldValueMap.put(GENDER, new FieldValue(GENDER));
        fieldValueMap.put(DNA_SOURCE, new FieldValue(DNA_SOURCE));
        fieldValueMap.put(COMMENTS, new FieldValue(COMMENTS));
    }


    public Collection<FieldValue> getFieldValueMap() {
        return fieldValueMap.values();
    }

    public FieldValue getFieldValue(String field) {
        return fieldValueMap.get(field);
    }

    public String get(String fieldName) {
        return fieldValueMap.get(fieldName).getCellValue();
    }

    public FieldValue getId() {
        return fieldValueMap.get(ID);
    }

    public void setId(FieldValue id) {
        this.fieldValueMap.put(ID, id);
    }

    public FieldValue getPanel() {
        return fieldValueMap.get(PANEL);
    }

    public void setPanel(FieldValue panel) {
        this.fieldValueMap.put(PANEL, panel);
    }

    public FieldValue getProject() {
        return fieldValueMap.get(PROJECT);
    }

    public void setProject(FieldValue project) {
        this.fieldValueMap.put(PROJECT, project);
    }

    public FieldValue getGender() {
        return fieldValueMap.get(GENDER);
    }

    public void setGender(FieldValue gender) {
        this.fieldValueMap.put(GENDER, gender);
    }

    public FieldValue getDnaSource() {
        return fieldValueMap.get(DNA_SOURCE);
    }

    public void setDnaSource(FieldValue dnaSource) {
        this.fieldValueMap.put(DNA_SOURCE, dnaSource);
    }

    public FieldValue getComments() {
        return fieldValueMap.get(COMMENTS);
    }

    public void setComments(FieldValue comments) {
        this.fieldValueMap.put(COMMENTS, comments);
    }

    public boolean isValid() {
        for (FieldValue f : fieldValueMap.values()) {
            if (f.getEvalResult().equals(ValidationState.INVALID) ||
                f.getEvalResult().equals(ValidationState.NOT_VALIDATED)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get all the messages of the sample fields and return as a string.
     *
     * @return string of all the fields messages.
     */
    public String getMessages() {
        StringBuffer sb = new StringBuffer();
        for (FieldValue f : fieldValueMap.values()) {
            sb.append(f.getMessage());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Sample{");
        sb.append("id=").append(getId());
        sb.append(", panel=").append(getPanel());
        sb.append(", project=").append(getProject());
        sb.append(", gender=").append(getGender());
        sb.append(", dnaSource=").append(getDnaSource());
        sb.append(", comments=").append(getComments());
        sb.append('}');
        return sb.toString();
    }
}
