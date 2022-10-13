package com.infosetgroup.flexevent.dto;


public class BookingVerify {
    private String codeItem;
    private String date;

    public BookingVerify() {
    }

    public BookingVerify(String codeItem, String date) {
        this.codeItem = codeItem;
        this.date = date;
    }

    public String getCodeItem() {
        return codeItem;
    }

    public void setCodeItem(String codeItem) {
        this.codeItem = codeItem;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
