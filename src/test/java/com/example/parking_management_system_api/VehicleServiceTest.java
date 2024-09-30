package com.example.parking_management_system_api;
import com.example.parking_management_system_api.entities.Vehicle;
import com.example.parking_management_system_api.exception.InvalidFieldException;
import com.example.parking_management_system_api.exception.InvalidVehicleCategoryAndTypeException;
import com.example.parking_management_system_api.exception.VehicleNotFoundException;
import com.example.parking_management_system_api.repositories.VehicleRepository;
import com.example.parking_management_system_api.services.VehicleService;
import com.example.parking_management_system_api.web.dto.VehicleCreateDto;
import com.example.parking_management_system_api.web.dto.VehicleResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static com.example.parking_management_system_api.models.VehicleCategoryEnum.MONTHLY_PAYER;
import static com.example.parking_management_system_api.models.VehicleCategoryEnum.SEPARATED;
import static com.example.parking_management_system_api.models.VehicleTypeEnum.PASSENGER_CAR;
import static com.example.parking_management_system_api.models.VehicleTypeEnum.PUBLIC_SERVICE;
import static com.example.parking_management_system_api.web.dto.mapper.VehicleMapper.toVehicle;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class VehicleServiceTest {

    @InjectMocks // Cria uma instancia válida do service, com dependências mockadas
    private VehicleService vehicleService;

    @Mock
    private VehicleRepository vehicleRepository;

    @Test
    public void createVehicle_WithValidData_ReturnsVehicle() {
        VehicleCreateDto dto = new VehicleCreateDto();
        dto.setLicensePlate("ABC1234");
        dto.setCategory(SEPARATED);
        dto.setAccessType(PASSENGER_CAR);

        Vehicle vehicle = toVehicle(dto);

        // Mockando o comportamento do repository
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        // Executando o metodo de criação
        VehicleResponseDto sut = vehicleService.create(dto);

        assertThat(sut).isNotNull();
        assertThat(sut.getLicensePlate()).isEqualTo(dto.getLicensePlate());
        assertThat(sut.getCategory()).isEqualTo(dto.getCategory().name());
        assertThat(sut.getAccessType()).isEqualTo(dto.getAccessType().name());
    }

    @Test
    public void createVehicle_WithInvalidData_ThrowsException() {
        VehicleCreateDto invalidDto = new VehicleCreateDto();
        invalidDto.setLicensePlate("");
        invalidDto.setCategory(SEPARATED);
        invalidDto.setAccessType(PASSENGER_CAR);

        when(vehicleRepository.save(any(Vehicle.class))).thenThrow(InvalidFieldException.class);

        assertThatThrownBy(() -> vehicleService.create(invalidDto)).isInstanceOf(InvalidFieldException.class);
    }

    @Test
    public void createVehicle_WithInvalidCategoryByType_ThrowsException() {
        VehicleCreateDto invalidDto = new VehicleCreateDto();
        invalidDto.setLicensePlate("ABC1234");
        invalidDto.setCategory(MONTHLY_PAYER);
        invalidDto.setAccessType(PUBLIC_SERVICE);

        when(vehicleRepository.save(any(Vehicle.class))).thenThrow(InvalidVehicleCategoryAndTypeException.class);

        assertThatThrownBy(() -> vehicleService.create(invalidDto)).isInstanceOf(InvalidVehicleCategoryAndTypeException.class);
    }

    @Test
    public void getVehicle_ByExistingId_ReturnsVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setLicensePlate("ABC123");
        vehicle.setCategory(SEPARATED);
        vehicle.setAccessType(PASSENGER_CAR);

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));

        Vehicle sut = vehicleService.findById(1L);

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(vehicle);
    }

    @Test
    public void getVehicle_ByUnexistingId_ThrowsException() {
        // Mockando o retorno do repositório para um ID inexistente
        when(vehicleRepository.findById(100L)).thenReturn(Optional.empty());

        // Verificando se o metodo lança a exceção esperada
        assertThatThrownBy(() -> vehicleService.findById(100L))
                .isInstanceOf(VehicleNotFoundException.class);
    }

    @Test
    public void getVehicle_ByExistingLicensePlate_ReturnsVehicle() {
        Vehicle vehicle = new Vehicle();
        when(vehicleRepository.findByLicensePlate("ABC1234")).thenReturn(Optional.of(vehicle));

        Vehicle sut = vehicleService.findByLicensePlate("ABC1234");

        assertThat(sut).isNotNull();
        assertThat(sut).isEqualTo(vehicle);
    }

    @Test
    public void getVehicle_ByUnexistingLicensePlate_ThrowsException() {
        Vehicle vehicle = new Vehicle();
        when(vehicleRepository.findByLicensePlate("AEI1234")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> vehicleService.findByLicensePlate("AEI1234"))
                .isInstanceOf(VehicleNotFoundException.class);
    }

    @Test
    public void updateVehicle_WithValidData_ReturnsVehicle() {
        VehicleCreateDto dto = new VehicleCreateDto();
        dto.setLicensePlate("ABC1234");
        dto.setCategory(SEPARATED);
        dto.setAccessType(PASSENGER_CAR);

        Vehicle vehicle = toVehicle(dto);
        vehicle.setId(1L);

        // Mockando o comportamento do repository na criação
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        // Mockando o comportamento do repository para encontrar o veículo existente
        when(vehicleRepository.findById(vehicle.getId())).thenReturn(Optional.of(vehicle));

        VehicleResponseDto sut = vehicleService.create(dto);

        VehicleCreateDto updateDto = new VehicleCreateDto();
        updateDto.setLicensePlate("NOVAPLACA123");
        updateDto.setCategory(SEPARATED);
        updateDto.setAccessType(PASSENGER_CAR);

        Vehicle updatedVehicle = toVehicle(updateDto);
        updatedVehicle.setId(vehicle.getId());

        // Mockando o comportamento do repository na atualização
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(updatedVehicle);

        Vehicle sutUpdate = vehicleService.update(vehicle.getId(), updateDto);

        assertThat(sutUpdate).isNotNull();
        assertThat(sutUpdate.getLicensePlate()).isEqualTo(updateDto.getLicensePlate());
    }

    @Test
    public void updateVehicle_WithInvalidData_ThrowsException() {
        VehicleCreateDto dto = new VehicleCreateDto();
        dto.setLicensePlate("ABC1234");
        dto.setCategory(SEPARATED);
        dto.setAccessType(PASSENGER_CAR);

        Vehicle vehicle = toVehicle(dto);
        vehicle.setId(1L);

        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        when(vehicleRepository.findById(vehicle.getId())).thenReturn(Optional.of(vehicle));

        VehicleResponseDto sut = vehicleService.create(dto);

        VehicleCreateDto updateDto = new VehicleCreateDto();
        updateDto.setLicensePlate("");
        updateDto.setCategory(SEPARATED);
        updateDto.setAccessType(PASSENGER_CAR);

        Vehicle updatedVehicle = toVehicle(updateDto);
        updatedVehicle.setId(vehicle.getId());

        when(vehicleRepository.save(any(Vehicle.class))).thenThrow(InvalidFieldException.class);

        assertThatThrownBy(() -> vehicleService.update(vehicle.getId(), updateDto)).isInstanceOf(InvalidFieldException.class);
    }

    @Test
    public void updateVehicle_ByUnexistingId_ThrowsException() {
        VehicleCreateDto dto = new VehicleCreateDto();
        dto.setLicensePlate("ABC1234");
        dto.setCategory(SEPARATED);
        dto.setAccessType(PASSENGER_CAR);

        Vehicle vehicle = toVehicle(dto);
        vehicle.setId(1L);

        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        when(vehicleRepository.findById(vehicle.getId())).thenReturn(Optional.of(vehicle));

        VehicleResponseDto sut = vehicleService.create(dto);

        VehicleCreateDto updateDto = new VehicleCreateDto();
        updateDto.setLicensePlate("NOVAPLACA123");
        updateDto.setCategory(SEPARATED);
        updateDto.setAccessType(PASSENGER_CAR);

        Vehicle updatedVehicle = toVehicle(updateDto);
        updatedVehicle.setId(vehicle.getId());

        when(vehicleRepository.save(any(Vehicle.class))).thenThrow(VehicleNotFoundException.class);

        assertThatThrownBy(() -> vehicleService.update(2L, updateDto)).isInstanceOf(VehicleNotFoundException.class);
    }

    @Test
    public void updateVehicle_WithInvalidCategoryByType_ThrowsException(){
        VehicleCreateDto dto = new VehicleCreateDto();
        dto.setLicensePlate("ABC1234");
        dto.setCategory(SEPARATED);
        dto.setAccessType(PASSENGER_CAR);

        Vehicle vehicle = toVehicle(dto);
        vehicle.setId(1L);

        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        when(vehicleRepository.findById(vehicle.getId())).thenReturn(Optional.of(vehicle));

        VehicleResponseDto sut = vehicleService.create(dto);

        VehicleCreateDto updateDto = new VehicleCreateDto();
        updateDto.setLicensePlate("NOVAPLACA123");
        updateDto.setCategory(SEPARATED);
        updateDto.setAccessType(PUBLIC_SERVICE);

        Vehicle updatedVehicle = toVehicle(updateDto);
        updatedVehicle.setId(vehicle.getId());

        when(vehicleRepository.save(any(Vehicle.class))).thenThrow(InvalidVehicleCategoryAndTypeException.class);

        assertThatThrownBy(() -> vehicleService.update(1L, updateDto)).isInstanceOf(InvalidVehicleCategoryAndTypeException.class);
    }

    @Test
    public void deleteVehicle_WithExistingId_doesNotThrowAnyException(){
        VehicleCreateDto dto = new VehicleCreateDto();
        dto.setLicensePlate("ABC1234");
        dto.setCategory(SEPARATED);
        dto.setAccessType(PASSENGER_CAR);

        Vehicle vehicle = toVehicle(dto);
        vehicle.setId(1L);

        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        // Mockando o comportamento do repository para encontrar o veículo
        when(vehicleRepository.findById(vehicle.getId())).thenReturn(Optional.of(vehicle));

        // Mockando o comportamento do repository para deletar o veículo pelo ID
        doNothing().when(vehicleRepository).deleteById(1L);

        assertThatCode(() -> vehicleService.delete(1L)).doesNotThrowAnyException();
    }

    @Test
    public void deleteVehicle_WithUnexistingId_ThrowsException(){
        VehicleCreateDto dto = new VehicleCreateDto();
        dto.setLicensePlate("ABC1234");
        dto.setCategory(SEPARATED);
        dto.setAccessType(PASSENGER_CAR);

        Vehicle vehicle = toVehicle(dto);
        vehicle.setId(1L);

        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        assertThatThrownBy(() -> vehicleService.delete(2L)).isInstanceOf(VehicleNotFoundException.class);
    }
}
