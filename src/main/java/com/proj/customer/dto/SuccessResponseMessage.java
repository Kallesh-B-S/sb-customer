package com.proj.customer.dto;

public class SuccessResponseMessage {
    private String message;

    public SuccessResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
