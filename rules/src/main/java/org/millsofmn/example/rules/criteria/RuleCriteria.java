package org.millsofmn.example.rules.criteria;

public interface RuleCriteria<T> {

    boolean evaluate(T sample);
}
