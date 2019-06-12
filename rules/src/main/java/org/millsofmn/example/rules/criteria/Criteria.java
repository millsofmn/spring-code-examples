package org.millsofmn.example.rules.criteria;

public interface Criteria<T> {
    boolean evaluate(T data);
}
