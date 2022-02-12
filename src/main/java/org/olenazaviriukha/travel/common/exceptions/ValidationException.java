package org.olenazaviriukha.travel.common.exceptions;

import java.util.Map;

public class ValidationException extends Exception{
    private Map<String, String> errors;
    private Object obj;
    public ValidationException(Object obj, Map<String, String> errors) {
        this.errors = errors;
        this.obj = obj;
    }

    public Map <String, String> getErrors() {
        return this.errors;
    }

    public Object getObject() {
        return this.obj;
    }
}
