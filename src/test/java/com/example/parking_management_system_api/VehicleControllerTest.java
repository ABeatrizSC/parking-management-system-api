package com.example.parking_management_system_api;

import com.example.parking_management_system_api.services.VehicleService;
import com.example.parking_management_system_api.web.controller.VehicleController;
import com.example.parking_management_system_api.web.dto.VehicleCreateDto;
import com.example.parking_management_system_api.web.dto.VehicleResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static com.example.parking_management_system_api.VehicleConstants.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.when;

@WebMvcTest(VehicleController.class)
public class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VehicleService vehicleService;

    @Test
    public void createVehicle_WithValidData_ReturnsStatus201 () throws Exception{
        when(vehicleService.create(VEHICLE6)).thenReturn(VEHICLE6RESPONSE);
                //.thenReturn(new ResponseEntity<>(vehicleResponseDto, HttpStatus.CREATED));
        mockMvc.perform(post("/api/vehicles")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(VEHICLE6RESPONSE)))
                .andExpect(status().isCreated());
    }

}
