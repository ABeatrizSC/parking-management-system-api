package com.example.parking_management_system_api.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TicketCreateDto {
    @NotBlank
    private LocalTime startHour;
}
