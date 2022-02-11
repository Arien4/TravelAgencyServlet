package org.olenazaviriukha.travel.dao;

public class DuplicateKeyException extends Exception {
    String param;

    public String getParam() {
        return param;
    }

    public DuplicateKeyException(String param, String reason) {
        super(reason);
        this.param = param;
    }
}
