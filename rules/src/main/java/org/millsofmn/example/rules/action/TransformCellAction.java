package org.millsofmn.example.rules.action;

import org.millsofmn.example.rules.sample.Sample;

public class TransformCellAction implements Action<Sample> {

    private String columnName;
    private Action action;

    public static TransformCellAction transformFieldValueColumn(String columnName){
        return new TransformCellAction(columnName);
    }

    private TransformCellAction(String columnName) {
        this.columnName = columnName;
    }

    public TransformCellAction toUppercase(){
        action = (Action<Sample>) sample -> sample.getFieldValue(columnName).setCellValue(
                sample.getFieldValue(columnName).getCellValue().toUpperCase()
        );
        return this;
    }

    public TransformCellAction toLowercase(){
        action = (Action<Sample>) sample -> sample.getFieldValue(columnName).setCellValue(
                sample.getFieldValue(columnName).getColumnName().toLowerCase()
        );
        return this;
    }

    @Override
    public void execute(Sample sample) {
        action.execute(sample);
    }
}
