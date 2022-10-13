package com.infosetgroup.flexevent.dto;

public class CategoryDTO {
    private String code;
    private String name;
    private String wording;
    private String icon;

    public CategoryDTO() {
    }

    public CategoryDTO(String code, String name, String wording, String icon) {
        this.code = code;
        this.name = name;
        this.wording = wording;
        this.icon = icon;
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

    public String getWording() {
        return wording;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
