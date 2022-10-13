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
public class GetFavoriteResponse {
    private boolean status;
    private String message;
    private List<ItemDTO> items = new ArrayList<>();
}
