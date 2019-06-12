package org.millsofmn.example.rules.criteria.ruletype;

import org.millsofmn.example.rules.Sample;
import org.millsofmn.example.rules.criteria.RuleCriteria;

public class PanelRuleCriteria implements RuleCriteria<Sample> {

    private String panelMnemonic;

    public static PanelRuleCriteria itIsPanelRuleForMnemonic(String panelMnemonic){
        return new PanelRuleCriteria(panelMnemonic);
    }

    public PanelRuleCriteria(String panelMnemonic) {
        this.panelMnemonic = panelMnemonic;
    }

    @Override
    public boolean evaluate(Sample sample) {
        if( sample.getFieldValue(Sample.PANEL).getCellValue().equalsIgnoreCase(panelMnemonic)){
            System.out.println("for panel " + panelMnemonic);
            return true;
        }
        return false;
    }
}
