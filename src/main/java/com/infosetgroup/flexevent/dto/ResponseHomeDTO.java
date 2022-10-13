package com.infosetgroup.flexevent.dto;

import java.util.ArrayList;
import java.util.List;

public class ResponseHomeDTO {
    private String message;
    List<CategoryDTO> categories = new ArrayList<>();

    public ResponseHomeDTO() {
    }

    public ResponseHomeDTO(String message, List<CategoryDTO> categories) {
        this.message = message;
        this.categories = categories;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }
}
