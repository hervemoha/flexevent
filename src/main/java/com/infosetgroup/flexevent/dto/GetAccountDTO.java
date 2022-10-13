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
public class GetAccountDTO {
    private boolean status;
    private String firstname;
    private String lastname;
    private String username;
    private String cityCode;
    private String phone;
    private String phoneCountry;
    private String email;
    private String message;
    private List<ItemDTO> favorites = new ArrayList<>();
}
