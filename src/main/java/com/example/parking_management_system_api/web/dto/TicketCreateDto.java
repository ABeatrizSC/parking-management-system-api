package com.example.parking_management_system_api.web.dto;

import lombok.*;

import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TicketCreateDto {
    private LocalTime startHour;
}
