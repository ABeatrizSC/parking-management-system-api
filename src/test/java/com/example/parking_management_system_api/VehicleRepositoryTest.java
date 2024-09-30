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

import static com.example.parking_management_system_api.VehicleConstants.VEHICLE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class VehicleRepositoryTest {
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void createVehicle_WithValidData_ReturnsVehicle (){
        Vehicle vehicle = vehicleRepository.save(VEHICLE);
        Vehicle sut = testEntityManager.find(Vehicle.class, vehicle.getId());
        assertThat(sut).isNotNull();
        assertThat(sut.getLicensePlate()).isEqualTo("testplate1");
        assertThat(sut.getCategory()).isEqualTo(VehicleCategoryEnum.SEPARATED);
        assertThat(sut.getAccessType()).isEqualTo(VehicleTypeEnum.PASSENGER_CAR);
        assertThat(sut.getRegistered()).isFalse();
    }
}
