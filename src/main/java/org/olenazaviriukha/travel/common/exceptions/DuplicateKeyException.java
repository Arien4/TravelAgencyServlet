package org.olenazaviriukha.travel.common.exceptions;

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
