package com.infosetgroup.flexevent.dto;

import java.util.ArrayList;
import java.util.List;

public class GlobalResponseFieldDTO {
    private boolean status;
    private String message;
    private String field;
    private List<ItemDTO> favorites = new ArrayList<>();

    public GlobalResponseFieldDTO() {
    }

    public GlobalResponseFieldDTO(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public GlobalResponseFieldDTO(boolean status, String message, String field) {
        this.status = status;
        this.message = message;
        this.field = field;
    }

    public GlobalResponseFieldDTO(boolean status, String message, String field, List<ItemDTO> favorites) {
        this.status = status;
        this.message = message;
        this.field = field;
        this.favorites = favorites;
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

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public List<ItemDTO> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<ItemDTO> favorites) {
        this.favorites = favorites;
    }
}
