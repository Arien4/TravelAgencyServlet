package org.olenazaviriukha.travel.controller.exceptions;

import org.olenazaviriukha.travel.users.models.User;

import java.util.Map;

public class UserValidationException extends Exception{
    private Map <String, String> errors;
    private User user;
    public UserValidationException(User user, Map<String, String> errors) {
        this.errors = errors;
        this.user = user;
    }

    public Map <String, String> getErrors() {
        return this.errors;
    }

    public User getUser() {
        return this.user;
    }
}
