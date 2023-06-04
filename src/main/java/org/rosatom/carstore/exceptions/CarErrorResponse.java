package org.rosatom.carstore.exceptions;

public class CarErrorResponse {
    private String message;
    public CarErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
