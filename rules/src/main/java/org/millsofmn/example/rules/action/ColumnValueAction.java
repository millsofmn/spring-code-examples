package org.millsofmn.example.rules.action;

import org.millsofmn.example.rules.sample.Sample;

public class ColumnValueAction implements Action<Sample> {

    private String columnName;
    private Action action;

    public static ColumnValueAction uppercase(String columnName){
        return new ColumnValueAction(columnName).toUppercase();
    }

    public static ColumnValueAction lowercase(String columnName){
        return new ColumnValueAction(columnName).toLowercase();
    }

    private ColumnValueAction(String columnName) {
        this.columnName = columnName;
    }

    private ColumnValueAction toUppercase(){
        action = (Action<Sample>) sample -> sample.getFieldValue(columnName).setCellValue(
                sample.getFieldValue(columnName).getCellValue().toUpperCase()
        );
        return this;
    }

    private ColumnValueAction toLowercase(){
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
