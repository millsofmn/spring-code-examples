package org.millsofmn.example.rules.action;

import org.millsofmn.example.rules.sample.Sample;

public class ApplyDefaultPanelAction implements Action<Sample> {

    private String panelColumnName;
    private String projectColumnName;
    private Action action;

    public static ApplyDefaultPanelAction findDefaultPanelForProject(String projectColumnName){
        return new ApplyDefaultPanelAction(projectColumnName);
    }

    private ApplyDefaultPanelAction(String projectColumnName) {
        this.projectColumnName = projectColumnName;
    }

    public ApplyDefaultPanelAction andSetValueInColumn(String panelColumnName){
        this.panelColumnName = panelColumnName;
        return this;
    }

    public ApplyDefaultPanelAction ifFound(Action action){
        this.action = action;
        return this;
    }

    @Override
    public void execute(Sample sample) {
        String thisValue = lookUpValue(sample.getFieldValue(projectColumnName).getCellValue());

        if(!thisValue.isEmpty()) {
            sample.getFieldValue(panelColumnName).setCellValue(thisValue);
            action.execute(sample);
        }
    }

    private String lookUpValue(String value){
        return "LookedUp";
    }
}
