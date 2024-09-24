package com.example.parking_management_system_api.web.controller;
import com.example.parking_management_system_api.entities.Vehicles;
import com.example.parking_management_system_api.services.VehiclesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicles")
public class VehiclesController {

    private final VehiclesService vehiclesService;


    @PostMapping
    public Object create (Vehicles vehicles){

        return new Object();
    }

    @GetMapping
    public Object read (@RequestParam(required = false) String plate){

        return new Object();
    }

    @PutMapping
    public Object update (@RequestBody Vehicles vehicles){
        return new Object();
    }

    @DeleteMapping
    public Void delete (){
        return null;
    }

}
