package com.example.parking_management_system_api.web.controller;
import com.example.parking_management_system_api.entities.Vehicle;
import com.example.parking_management_system_api.exception.InvalidFieldException;
import com.example.parking_management_system_api.services.VehicleService;
import com.example.parking_management_system_api.web.dto.VehicleCreateDto;
import com.example.parking_management_system_api.web.dto.VehicleResponseDto;
import com.example.parking_management_system_api.web.dto.mapper.VehicleMapper;
import com.example.parking_management_system_api.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Vehicles", description = "This section contains the endpoints for vehicle management, such as" +
        " creation, deletion and searching.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;

    @Operation(summary = "Creates a new vehicle in the database.", description = "Endpoint for creating" +
            " new vehicles via POST request.",
    responses = {
            @ApiResponse(responseCode = "201", description = "Vehicle created successfully.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Type incompatibility or invalid values.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)
                            , examples = @ExampleObject(value = "{ \"error\": \"Invalid fields.\" }")))
    })
    @PostMapping
    public ResponseEntity<VehicleResponseDto> create(@RequestBody @Valid VehicleCreateDto dto){
        VehicleResponseDto response = vehicleService.create(dto);
        return ResponseEntity.created(URI.create("/api/vehicles"
                + dto.getLicensePlate())).body(response);
    }


    @Operation(summary = "Returns all vehicles in the database.", description = "Endpoint for listing all vehicles" +
            " present in the database. The list will be empty if no vehicles are found.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Vehicle list returned.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleResponseDto.class)))
            })
    @GetMapping
    public ResponseEntity<List<Vehicle>> getAll(){
        List<Vehicle> vehicles = vehicleService.findAll();
        return ResponseEntity.ok(vehicles);
    }

    @Operation(summary = "Returns a given vehicle by ID.", description = "Endpoint for looking up a vehicle in the database" +
            " using ID as the parameter.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Vehicle listed.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Vehicle not found.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)
                                    , examples = @ExampleObject(value = "{ \"error\": \"Vehicle not found.\" }")))
            })
    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getById(@PathVariable @Valid Long id) {
        Vehicle vehicle = vehicleService.findById(id);
        return ResponseEntity.ok(vehicle);
    }

    @Operation(summary = "Returns a given vehicle by plate.", description = "Endpoint for looking up a vehicle in the database" +
            " using the license plate as the parameter.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Vehicle listed.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = VehicleResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Vehicle not found.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)
                                    , examples = @ExampleObject(value = "{ \"error\": \"Vehicle not found.\" }")))
            })
    @GetMapping("/licensePlate={licensePlate}")
    public ResponseEntity<VehicleResponseDto> getByLicensePlate(@PathVariable @Valid String licensePlate) {
        Vehicle vehicle = vehicleService.findByLicensePlate(licensePlate);
        return ResponseEntity.ok(VehicleMapper.toDto(vehicle));
    }

    @Operation(summary = "Updates a given vehicle by ID.", description = "Endpoint for updating up a vehicle in the database" +
            " using ID as the parameter.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No content, update successful.",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Vehicle not found.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)
                                    , examples = @ExampleObject(value = "{ \"error\": \"Vehicle not found.\" }"))),
                    @ApiResponse(responseCode = "400", description = "Type incompatibility or invalid values.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = InvalidFieldException.class)
                                    , examples = @ExampleObject(value = "{ \"error\": \"Invalid fields.\" }")))
            })
    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid VehicleCreateDto vehicle){
        vehicleService.update(id, vehicle);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deletes a given vehicle by ID.", description = "Endpoint for deleting up a vehicle in the database" +
            " using ID as the parameter.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No content, update successful.",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Vehicle not found.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)
                                    , examples = @ExampleObject(value = "{ \"error\": \"Vehicle not found.\" }")))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id){
        vehicleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
