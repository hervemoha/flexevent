package com.infosetgroup.flexevent.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FavoriteAddRequest {
    private String codeItem;
    private String codeCustomer;
}
