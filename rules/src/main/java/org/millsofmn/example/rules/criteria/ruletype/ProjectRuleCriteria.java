package org.millsofmn.example.rules.criteria.ruletype;

import org.millsofmn.example.rules.Sample;
import org.millsofmn.example.rules.criteria.RuleCriteria;

public class ProjectRuleCriteria implements RuleCriteria<Sample> {

    private String projectNumber;

    public static ProjectRuleCriteria itIsProjectRuleForProject(String projectNumber){
        return new ProjectRuleCriteria(projectNumber);
    }

    public ProjectRuleCriteria(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    @Override
    public boolean evaluate(Sample sample) {
        if( sample.getFieldValue(Sample.PROJECT).getCellValue().equalsIgnoreCase(projectNumber)){
            return true;
        }
        return false;
    }
}
