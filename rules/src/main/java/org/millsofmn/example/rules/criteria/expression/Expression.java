package org.millsofmn.example.rules.criteria.expression;

public interface Expression<T> {
    boolean evaluate(T value);
}
