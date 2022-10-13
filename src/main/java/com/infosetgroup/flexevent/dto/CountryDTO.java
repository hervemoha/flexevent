package com.infosetgroup.flexevent.dto;

public class CountryDTO {
    private String code;
    private String callingCode;
    private String name;

    public CountryDTO() {
    }

    public CountryDTO(String code, String callingCode, String name) {
        this.code = code;
        this.callingCode = callingCode;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCallingCode() {
        return callingCode;
    }

    public void setCallingCode(String callingCode) {
        this.callingCode = callingCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
