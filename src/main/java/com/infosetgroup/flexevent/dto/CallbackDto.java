package com.infosetgroup.flexevent.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CallbackDto {
    private String code;
    private String reference;

    public CallbackDto() {
    }

    public CallbackDto(String code, String reference) {
        this.code = code;
        this.reference = reference;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
