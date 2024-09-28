package com.example.parking_management_system_api.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum SlotTypeEnum {
    MONTHLY(200);

    @Setter
    private int quantity;
}
