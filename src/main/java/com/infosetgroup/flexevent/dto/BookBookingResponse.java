package com.infosetgroup.flexevent.dto;

public class BookBookingResponse {
    private boolean status;
    private String message;
    private String bookingCode;

    public BookBookingResponse() {
    }

    public BookBookingResponse(boolean status, String message, String bookingCode) {
        this.status = status;
        this.message = message;
        this.bookingCode = bookingCode;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }
}
