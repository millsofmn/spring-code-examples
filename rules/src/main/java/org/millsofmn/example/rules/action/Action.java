package org.millsofmn.example.rules.action;


public interface Action<T> {
    void execute(T object);
}
