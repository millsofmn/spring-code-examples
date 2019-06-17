package org.millsofmn.example.rules.sample;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Sample {

    // define the known columns
    public static final String ID = "ID";
    public static final String PLATE_ID = "PLATE_ID";
    public static final String PANEL = "PANEL";
    public static final String PROJECT = "PROJECT";
    public static final String PHASE = "PHASE";
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
        fieldValueMap.put(PLATE_ID, new FieldValue(PLATE_ID));
        fieldValueMap.put(PANEL, new FieldValue(PANEL));
        fieldValueMap.put(PROJECT, new FieldValue(PROJECT));
        fieldValueMap.put(PHASE, new FieldValue(PHASE));
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

    public FieldValue getPlateId() {
        return fieldValueMap.get(PLATE_ID);
    }

    public void setPlateId(FieldValue plateId) {
        this.fieldValueMap.put(PLATE_ID, plateId);
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

    public FieldValue getPhase() {
        return fieldValueMap.get(PHASE);
    }

    public void setPhase(FieldValue phase) {
        this.fieldValueMap.put(PHASE, phase);
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
        StringBuilder sb = new StringBuilder();
        for (FieldValue f : fieldValueMap.values()) {
            sb.append(f.getMessage());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("\r\nSample{");
        sb.append("\n\tid=").append(getId());
        sb.append("\n\tplateId=").append(getPlateId());
        sb.append("\n\tpanel=").append(getPanel());
        sb.append("\n\tproject=").append(getProject());
        sb.append("\n\tphase=").append(getPhase());
        sb.append("\n\tgender=").append(getGender());
        sb.append("\n\tdnaSource=").append(getDnaSource());
        sb.append("\n\tcomments=").append(getComments());
        sb.append('}');
        return sb.toString();
    }
}
