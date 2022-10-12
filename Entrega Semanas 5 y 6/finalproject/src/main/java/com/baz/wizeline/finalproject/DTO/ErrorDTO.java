package com.baz.wizeline.finalproject.DTO;

public class ErrorDTO {
    /*Uso de por lo menos dos beans: Bean ErrorDTO*/
    String errorCode;
    String message;

    public ErrorDTO(){
        super();
    }

    public ErrorDTO(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}