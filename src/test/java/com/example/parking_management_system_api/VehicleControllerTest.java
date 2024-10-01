package com.example.parking_management_system_api;

import com.example.parking_management_system_api.entities.Vehicle;
import com.example.parking_management_system_api.exception.EntityNotFoundException;
import com.example.parking_management_system_api.exception.InvalidFieldException;
import com.example.parking_management_system_api.exception.InvalidVehicleCategoryAndTypeException;
import com.example.parking_management_system_api.exception.VehicleNotFoundException;
import com.example.parking_management_system_api.services.VehicleService;
import com.example.parking_management_system_api.web.controller.VehicleController;
import com.example.parking_management_system_api.web.dto.VehicleCreateDto;
import com.example.parking_management_system_api.web.dto.VehicleResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.nio.file.Paths.get;
import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static com.example.parking_management_system_api.VehicleConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Test
    public void createVehicle_WithInvalidData_ReturnsStatus400 () throws Exception {
        mockMvc.perform(
                post("/api/vehicles")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(EMPTY_VEHICLE))

        ).andExpect(status().isBadRequest());
        mockMvc.perform(
                post("/api/vehicles")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(INVALID_VEHICLE))

        ).andExpect(status().isBadRequest());
    }

    @Test
    public void findVehicle_WithValidId_ReturnsStatus200 () throws Exception {
        when(vehicleService.findById(1L)).thenReturn(VEHICLE);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/vehicles/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void findVehicle_WithInvalidId_ReturnsStatus404 () throws Exception {
        when(vehicleService.findById(1L)).thenThrow(VehicleNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/vehicles/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findVehicle_WithValidLicensePlate_ReturnsStatus200 () throws Exception {
        when(vehicleService.findByLicensePlate("testplate1")).thenReturn(VEHICLE);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/vehicles/licensePlate=testplate1"))
                .andExpect(status().isOk());
    }

    @Test
    public void findVehicle_WithInvalidLicensePlate_ReturnsStatus404 () throws Exception {
        when(vehicleService.findByLicensePlate("testplate1")).thenThrow(VehicleNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/vehicles/licensePlate=testplate1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findVehicle_AllVehicles_ReturnsStatus200 () throws Exception {
       when(vehicleService.findAll()).thenReturn(LIST);
       mockMvc.perform(MockMvcRequestBuilders.get("/api/vehicles"))
               .andExpect(status().isOk());

    }

    @Test
    public void updateVehicle_WithValidIdAndData_ReturnsStatus204 () throws Exception {
        when(vehicleService.update(1L,VEHICLE6)).thenReturn(VEHICLE);
        mockMvc.perform(patch("/api/vehicles/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(VEHICLE6))
        ).andExpect(status().isNoContent());
    }

    @Test
    public void updateVehicle_WithValidIdAndInvalidData_ReturnsStatus400 () throws Exception {
        when(vehicleService.update(1L,INVALID_VEHICLE2)).thenThrow(InvalidFieldException.class);
        mockMvc.perform(patch("/api/vehicles/1")
                .contentType("application/json")
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void deleteVehicle_WithValidId_ReturnsStatus204 () throws Exception {
        mockMvc.perform(delete("/api/vehicles/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteVehicle_WithInvalidId_ReturnsStatus404 () throws Exception {
        final Long vehicleId = 1L;
        doThrow(new VehicleNotFoundException()).when(vehicleService).delete(vehicleId);
        mockMvc.perform(delete("/api/vehicles/"+vehicleId))
                .andExpect(status().isNotFound());
    }

}
