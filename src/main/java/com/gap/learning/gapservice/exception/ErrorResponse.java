package com.gap.learning.gapservice.exception;

public class ErrorResponse {

    private String message;
    private String details;
    private String errorCode;

    public ErrorResponse() {
    }

    public ErrorResponse(String message, String details, String errorCode) {
        this.message = message;
        this.details = details;
        this.errorCode = errorCode;
    }


    public String getMessage() {
        return message;
    }

    public ErrorResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public ErrorResponse setDetails(String details) {
        this.details = details;
        return this;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public ErrorResponse setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }
}
