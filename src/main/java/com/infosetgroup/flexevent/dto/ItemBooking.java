package com.infosetgroup.flexevent.dto;

public class ItemBooking {
    private String code;
    private String name;
    private String description;
    private String adress;
    private String icon;
    private String city;
    private String country;
    private String currency;
    private String price;

    public ItemBooking() {
    }

    public ItemBooking(String code, String name, String description, String adress, String icon, String city, String country, String currency, String price) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.adress = adress;
        this.icon = icon;
        this.city = city;
        this.country = country;
        this.currency = currency;
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
