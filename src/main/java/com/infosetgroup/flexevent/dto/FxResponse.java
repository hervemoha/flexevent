package com.infosetgroup.flexevent.dto;

public class FxResponse {
    private String code;
    private String message;
    private String orderNumber;

    public FxResponse() {
    }

    public FxResponse(String code, String message, String orderNumber) {
        this.code = code;
        this.message = message;
        this.orderNumber = orderNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
