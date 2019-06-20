package org.millsofmn.example.rules.action;

import org.millsofmn.example.rules.sample.Sample;

import java.util.*;

public class AlphabetizeCellAction implements Action<Sample> {

    private List<String> columnNames = new ArrayList<>();

    public static AlphabetizeCellAction alphabetize(String columnName) {
        return new AlphabetizeCellAction(columnName);
    }

    private AlphabetizeCellAction(String columnName) {
        this.columnNames.add(columnName);
    }

    public AlphabetizeCellAction and(String columnName) {
        this.columnNames.add(columnName);
        return this;
    }

    @Override
    public void execute(Sample sample) {
        Map<String, List<String>> columnValueMap = new TreeMap<>();

        List<String> keys = Arrays.asList(sample.getFieldValue(columnNames.get(0)).getCellValue().trim().split("\\s+"));

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);

            for (String columnName : columnNames) {
                if (!columnValueMap.containsKey(key)) {
                    columnValueMap.put(key, new ArrayList<>());
                }

                String value = sample.getFieldValue(columnName).getCellValue().trim().split("\\s+")[i];
                columnValueMap.get(key).add(value);
            }
        }

        for (int x = 0; x < columnNames.size(); x++) {
            StringBuilder colVal = new StringBuilder();

            for (Map.Entry<String, List<String>> entry : columnValueMap.entrySet()) {
                if(colVal.length() > 1) colVal.append(" ");
                colVal.append(entry.getValue().get(x));
            }
            sample.getFieldValue(columnNames.get(x)).setCellValue(colVal.toString());
        }

    }
}
