package org.millsofmn.example.rules.criteria.expression;

import java.util.List;

public class NotFoundInList extends FoundInList {

    public NotFoundInList(List<String> items) {
        super(items);
    }

    @Override
    public boolean evaluate(String value) {
        return !super.evaluate(value);
    }
}
