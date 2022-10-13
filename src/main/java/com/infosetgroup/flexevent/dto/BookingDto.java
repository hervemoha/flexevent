package com.infosetgroup.flexevent.dto;

public class BookingDto {
    private String codeItem;
    private String date;
    private String currency;
    private String amount;

    public BookingDto() {
    }

    public BookingDto(String codeItem, String date, String currency, String amount) {
        this.codeItem = codeItem;
        this.date = date;
        this.currency = currency;
        this.amount = amount;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
