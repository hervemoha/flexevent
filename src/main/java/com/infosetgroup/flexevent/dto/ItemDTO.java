package com.infosetgroup.flexevent.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemDTO {
    private String code;
    private String name;
    private String favorite;
    private String description;
    private String adress;
    private String icon;
    private String city;
    private String country;
    private String date;
    private String price;
    private String partialPrice;
    private String currency;
    private List<String> pictures = new ArrayList<>();
    private List<String> tags = new ArrayList<>();
    private List<String> specifications = new ArrayList<>();
}
