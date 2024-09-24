package com.example.parking_management_system_api.services;

import com.example.parking_management_system_api.entities.Vehicles;
import com.example.parking_management_system_api.repositories.VehiclesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VehiclesService {

    private final VehiclesRepository vehiclesRepository;

    public Vehicles create (){
        return new Vehicles();
    }

    public Object showVehicles(){
        return new Object();
    }

    public Object showAll(){
        return new Object();
    }

    public Object update(){
        return new Object();
    }

    public Object delete(){
        return new Object();
    }


}
