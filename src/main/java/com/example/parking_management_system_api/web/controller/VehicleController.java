package com.example.parking_management_system_api.web.controller;
import com.example.parking_management_system_api.entities.Vehicle;
import com.example.parking_management_system_api.services.VehicleService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;


    @PostMapping
    public Object create (Vehicle vehicles){

        return new Object();
    }

    @GetMapping
    public Object read (@RequestParam(required = false) String plate){

        return new Object();
    }

    @PutMapping
    public Object update (@RequestBody Vehicle vehicles){
        return new Object();
    }

    @DeleteMapping
    public Void delete (){
        return null;
    }

}
