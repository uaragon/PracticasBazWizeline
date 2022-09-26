package com.wizeline.DTO;

public class ResponseDTO {
    /**
     * Status: Guarda el resultado del request, posibles valores success o fail.
     */
    private String status;
    /**
     * Code: Guarda el codigo de la operacion realizada o error.
     */
    private String code;

    /**
     * Error: Bean que maneja un listado de errores presentados en durante el procesamiento de request.
     */
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
