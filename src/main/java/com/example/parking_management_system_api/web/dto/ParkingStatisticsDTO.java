package com.example.parking_management_system_api.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingStatisticsDTO {

    private int avulsasOcupadas;
    private int capacidadeAvulsas;
    private int mensalistasOcupadas;
    private int capacidadeMensalistas;
}
