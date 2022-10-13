package com.infosetgroup.flexevent.dto;

public class InscriptionRequestDTO {
    private String firstname;
    private String lastname;
    private String username;
    private String password1;
    private String password2;
    private String cityCode;
    private String phone;
    private String phoneCountry;
    private String email;

    public InscriptionRequestDTO() {
    }

    public InscriptionRequestDTO(String firstname, String lastname, String username, String password1, String password2, String cityCode, String phone, String phoneCountry, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password1 = password1;
        this.password2 = password2;
        this.cityCode = cityCode;
        this.phone = phone;
        this.phoneCountry = phoneCountry;
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneCountry() {
        return phoneCountry;
    }

    public void setPhoneCountry(String phoneCountry) {
        this.phoneCountry = phoneCountry;
    }
}
