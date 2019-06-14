package org.millsofmn.example.rules.criteria;

public interface Expression<T> {
    boolean evaluate(T value);
}
