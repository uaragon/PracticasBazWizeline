package com.baz.wizeline.javamaven.model;

public class ResponseDTO {

    private String status;

    public Object getResultado() {
        return resultado;
    }

    public void setResultado(Object resultado) {
        this.resultado = resultado;
    }

    private String code;

    private Object resultado;
    public ResponseDTO(){
    }

    public ResponseDTO(String status, String code, Object obj){
        this.status = status;
        this.code = code;
        this.resultado = obj;
    }

    private ErrorDTO errors = new ErrorDTO();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ErrorDTO getErrors() {
        return errors;
    }

    public void setErrors(ErrorDTO errors) {
        this.errors = errors;
    }
}