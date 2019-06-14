package org.millsofmn.example.rules.criteria.sample;

import org.millsofmn.example.rules.criteria.Expression;

import java.util.ArrayList;
import java.util.List;

public class FoundInList implements Expression<String> {

    private List<String> items = new ArrayList<>();

    public FoundInList(List<String> items) {
        this.items = items;
    }

    @Override
    public boolean evaluate(String value) {
        return items.contains(value);
    }
}
