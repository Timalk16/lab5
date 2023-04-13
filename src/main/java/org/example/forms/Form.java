package org.example.forms;

import org.example.exceptions.FormException;
import org.example.exceptions.ScriptInputException;

public abstract class Form<T> {
    public abstract T build() throws FormException, ScriptInputException;
}
