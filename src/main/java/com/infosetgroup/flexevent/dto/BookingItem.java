package com.infosetgroup.flexevent.dto;

public class BookingItem {
    private String code;
    private String amount;
    private String currency;
    private boolean paid;
    private boolean closed;
    private String date;
    private ItemBooking item;

    public BookingItem() {
    }

    public BookingItem(String code, String amount, String currency, boolean paid, boolean closed, String date, ItemBooking item) {
        this.code = code;
        this.amount = amount;
        this.currency = currency;
        this.paid = paid;
        this.closed = closed;
        this.date = date;
        this.item = item;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ItemBooking getItem() {
        return item;
    }

    public void setItem(ItemBooking item) {
        this.item = item;
    }
}
