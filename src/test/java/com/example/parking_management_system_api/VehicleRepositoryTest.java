package com.example.parking_management_system_api;

import com.example.parking_management_system_api.entities.Vehicle;
import com.example.parking_management_system_api.models.VehicleCategoryEnum;
import com.example.parking_management_system_api.models.VehicleTypeEnum;
import com.example.parking_management_system_api.repositories.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static com.example.parking_management_system_api.VehicleConstants.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DataJpaTest
@ActiveProfiles("test")
public class VehicleRepositoryTest {
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @Transactional
    public void createVehicle_WithValidData_ReturnsVehicle (){
        Vehicle vehicle = vehicleRepository.save(VEHICLE);
        Vehicle sut = testEntityManager.find(Vehicle.class, vehicle.getId());
        assertThat(sut).isNotNull();
        assertThat(sut.getLicensePlate()).isEqualTo("testplate1");
        assertThat(sut.getCategory()).isEqualTo(VehicleCategoryEnum.SEPARATED);
        assertThat(sut.getAccessType()).isEqualTo(VehicleTypeEnum.PASSENGER_CAR);
        assertThat(sut.getRegistered()).isFalse();
    }

    @Test
    @Transactional
    public void createVehicle_WithNullOrInvalidData_ThrowsException(){
        assertThatThrownBy(()-> vehicleRepository.save(EMPTY_VEHICLE));
        assertThatThrownBy(()-> vehicleRepository.save(INVALID_VEHICLE));
    }

    @Test
    @Transactional
    public void createVehicle_WithExistingPlate_ThrowsException() {
        Vehicle vehicle = testEntityManager.persistFlushFind(VEHICLE2);
        testEntityManager.detach(vehicle);
        vehicle.setId(null);
        assertThatThrownBy(()-> vehicleRepository.save(vehicle));
    }

    @Test
    @Transactional
    public void deleteVehicle_WithExistingId_ReturnsNoContent(){
        Vehicle vehicle = testEntityManager.persistFlushFind(VEHICLE3);
        testEntityManager.detach(vehicle);
        vehicleRepository.deleteById(vehicle.getId());
        assertThat(vehicleRepository.findById(1L)).isEmpty();
    }

    @Test
    @Transactional
    public void findVehicle_WithValidId_ReturnsVehicle(){
        Vehicle vehicle = testEntityManager.persistFlushFind(VEHICLE4);
        testEntityManager.detach(vehicle);
        assertThat(vehicleRepository.findById(vehicle.getId())).isPresent();
    }

    @Test
    @Transactional
    public void findVehicle_WithInvalidId_ReturnsNoContent(){
        Vehicle vehicle = testEntityManager.persistFlushFind(VEHICLE5);
        testEntityManager.detach(vehicle);
        assertThat(vehicleRepository.findById(10L)).isEmpty();
    }
}
