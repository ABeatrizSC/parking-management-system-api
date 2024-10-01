package com.example.parking_management_system_api.web.dto;

import lombok.Data;

@Data
public class ParkingSpaceBatchDto {

    private int monthlyQuantity; // Quantidade de vagas mensais (MONTHLY)
    private int casualQuantity;  // Quantidade de vagas avulsas (CASUAL)
}
