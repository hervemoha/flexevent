package com.infosetgroup.flexevent.dto;

public class BookingPay {
    private String codeBooking;
    private int type;
    private String phone;

    public BookingPay() {
    }

    public BookingPay(String codeBooking, int type, String phone) {
        this.codeBooking = codeBooking;
        this.type = type;
        this.phone = phone;
    }

    public String getCodeBooking() {
        return codeBooking;
    }

    public void setCodeBooking(String codeBooking) {
        this.codeBooking = codeBooking;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
