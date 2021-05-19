package com.oui.integersets.response;

import java.util.Arrays;
import java.util.List;

public class ApiErrorResponse {
    private final Boolean success = false;
    private String message;
    private List<String> errors;

    public ApiErrorResponse() {
    }

    public ApiErrorResponse(String message, List<String> errors) {
        super();
        this.message = message;
        this.errors = errors;
    }

    public ApiErrorResponse(String message, String error) {
        super();
        this.message = message;
        errors = Arrays.asList(error);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public Boolean getSuccess() {
        return success;
    }

}
