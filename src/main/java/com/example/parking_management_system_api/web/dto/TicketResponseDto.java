package com.example.parking_management_system_api.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TicketResponseDto {
    private Long id;
    private LocalTime startHour;
    private LocalTime finishHour;
    private Double totalValue;
}
