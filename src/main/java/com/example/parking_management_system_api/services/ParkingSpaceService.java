package com.example.parking_management_system_api.services;

import com.example.parking_management_system_api.entities.ParkingSpace;
import com.example.parking_management_system_api.entities.Vehicle;
import com.example.parking_management_system_api.exception.InsufficientParkingSpacesException;
import com.example.parking_management_system_api.exception.InvalidCapacityException;
import com.example.parking_management_system_api.exception.InvalidFieldException;
import com.example.parking_management_system_api.models.SlotTypeEnum;
import com.example.parking_management_system_api.models.VehicleCategoryEnum;
import com.example.parking_management_system_api.repositories.ParkingSpaceRepository;
import com.example.parking_management_system_api.repositories.VehicleRepository;
import com.example.parking_management_system_api.web.dto.ParkingSpaceAvailabilityDto;
import com.example.parking_management_system_api.web.dto.ParkingSpaceBatchDto;
import com.example.parking_management_system_api.web.dto.ParkingSpaceCreateDto;
import com.example.parking_management_system_api.web.dto.ParkingSpaceReductionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParkingSpaceService {

    private final ParkingSpaceRepository parkingSpacesRepository;
    private final ParkingSpaceRepository parkingSpaceRepository;


    public ParkingSpaceAvailabilityDto getParkingSpaceAvailability(ParkingSpace parkingSpace) {
        int causalOccupied = (int) parkingSpaceRepository.countBySlotTypeAndOccupied(SlotTypeEnum.CASUAL,true);
        int casualCapacity = parkingSpaceRepository.countBySlotType(SlotTypeEnum.CASUAL);

        int monthlyOccupied = (int)parkingSpaceRepository.countBySlotTypeAndOccupied(SlotTypeEnum.MONTHLY,true);
        int monthlyCapacity = parkingSpaceRepository.countBySlotType(SlotTypeEnum.MONTHLY);

        ParkingSpaceAvailabilityDto parkingSpaceAvailabilityDto = new ParkingSpaceAvailabilityDto();
        parkingSpaceAvailabilityDto.setCasualSpotsOccupied(causalOccupied);
        parkingSpaceAvailabilityDto.setCasualCapacity(casualCapacity);
        parkingSpaceAvailabilityDto.setMonthlySpotsOccupied(monthlyOccupied);
        parkingSpaceAvailabilityDto.setMonthlyCapacity(monthlyCapacity);

        return parkingSpaceAvailabilityDto;
    }

    public void reduceParkingSpace(ParkingSpaceReductionDto parkingSpaceReductionDto) {
        // Remover vagas mensais
        if (parkingSpaceReductionDto.getMonthlyReduction() <= 0 || parkingSpaceReductionDto.getCasualReduction() <= 0) {
            throw new InvalidCapacityException("A capacidade requisitada é inválida.");
        }

        List<ParkingSpace> monthlySpaces = parkingSpaceRepository.findBySlotTypeAndOccupied(SlotTypeEnum.MONTHLY, false);
        int occupiedMonthlySpaces = (int) parkingSpaceRepository.countBySlotTypeAndOccupied(SlotTypeEnum.MONTHLY,true);
        if (occupiedMonthlySpaces > (monthlySpaces.size() - parkingSpaceReductionDto.getMonthlyReduction())) {
            throw new InsufficientParkingSpacesException("A capacidade requisitada é menor que a quantidade atual de veículos estacionados.");
        }


        if (monthlySpaces.size() >= parkingSpaceReductionDto.getMonthlyReduction()) {
            List<ParkingSpace> spacesToRemove = monthlySpaces.stream()
                    .limit(parkingSpaceReductionDto.getMonthlyReduction())
                    .collect(Collectors.toList());

            for (ParkingSpace space : spacesToRemove) {
                parkingSpacesRepository.delete(space);
            }
        } else {
            throw new IllegalArgumentException("Não há vagas mensais suficientes para remover");
        }

        // Remover vagas casuais
        List<ParkingSpace> casualSpaces = parkingSpaceRepository.findBySlotTypeAndOccupied(SlotTypeEnum.CASUAL, false);
        int occupiedCasualSpaces = (int) parkingSpaceRepository.countBySlotTypeAndOccupied(SlotTypeEnum.CASUAL, true);

        // Verifica se a quantidade de vagas a serem removidas não é menor do que a quantidade de veículos estacionados
        if (occupiedCasualSpaces > (casualSpaces.size() - parkingSpaceReductionDto.getCasualReduction())) {
            throw new InsufficientParkingSpacesException("A capacidade requisitada é menor que a quantidade atual de veículos estacionados.");
        }
        if (casualSpaces.size() >= parkingSpaceReductionDto.getCasualReduction()) {
            List<ParkingSpace> spacesToRemove = casualSpaces.stream()
                    .limit(parkingSpaceReductionDto.getCasualReduction())
                    .collect(Collectors.toList());

            for (ParkingSpace space : spacesToRemove) {
                parkingSpacesRepository.delete(space);
            }
        } else {
            throw new IllegalArgumentException("Não há vagas casuais suficientes para remover");
        }

        // Renumerar após a remoção
        renumberParkingSpaces();
    }

    private void renumberParkingSpaces() {

        List<ParkingSpace> allSpaces = parkingSpaceRepository.findAllByOrderByNumberAsc();


        Set<Integer> occupiedNumbers = new HashSet<>();


        for (ParkingSpace space : allSpaces) {
            if (space.isOccupied()) {
                occupiedNumbers.add(space.getNumber());
            }
        }

        int newNumber = 1;


        for (ParkingSpace space : allSpaces) {
            if (!space.isOccupied()) {

                while (occupiedNumbers.contains(newNumber)) {
                    newNumber++;
                }

                space.setNumber(newNumber);
                newNumber++;
            }
        }


        parkingSpacesRepository.saveAll(allSpaces);
    }

    public void createMultipleParkingSpaces(ParkingSpaceBatchDto parkingSpaceBatchDto){

        int monthlyQuantity = parkingSpaceBatchDto.getMonthlyQuantity();
        int casualQuantity = parkingSpaceBatchDto.getCasualQuantity();


        for(int i = 0 ; i < monthlyQuantity; i++ ){

            ParkingSpace parkingSpace = new ParkingSpace();

            Integer maxNumber = parkingSpaceRepository.findMaxParkingSpaces();
            int nextNumber = (maxNumber == null) ? 1 : maxNumber + 1;
            parkingSpace.setNumber(nextNumber);
            parkingSpace.setOccupied(false);
            parkingSpace.setSlotType(SlotTypeEnum.MONTHLY);

            parkingSpacesRepository.save(parkingSpace);
        }

        //vagas casual

        for(int i = 0 ; i < casualQuantity; i++ ){

            ParkingSpace parkingSpace = new ParkingSpace();

            Integer maxNumber = parkingSpaceRepository.findMaxParkingSpaces();
            int nextNumber = (maxNumber == null) ? 1 : maxNumber + 1;
            parkingSpace.setNumber(nextNumber);
            parkingSpace.setOccupied(false);
            parkingSpace.setSlotType(SlotTypeEnum.CASUAL);

            parkingSpacesRepository.save(parkingSpace);
        }


    }


    public void createParkingSpace(ParkingSpaceCreateDto parkingSpaceCreateDto) {
        ParkingSpace parkingSpace = new ParkingSpace();

        Integer maxNumber = parkingSpaceRepository.findMaxParkingSpaces();
        int nextNumber = (maxNumber == null) ? 1 : maxNumber +1;
        parkingSpace.setNumber(nextNumber);
        parkingSpace.setOccupied(parkingSpaceCreateDto.isOccupied());
        parkingSpace.setSlotType(SlotTypeEnum.valueOf(parkingSpaceCreateDto.getSlotType()));

        parkingSpacesRepository.save(parkingSpace);
    }

    public Optional<ParkingSpace> findParkingSpaceById(Long id) {
        return parkingSpacesRepository.findById(id);
    }

    public List<ParkingSpace> getAllParkingSpaces() {
        return parkingSpacesRepository.findAll();
    }

    public ParkingSpace updateParkingSpace(ParkingSpace parkingSpaces) {
        return parkingSpacesRepository.save(parkingSpaces);
    }

    public void deleteParkingSpace(Long id) {
        parkingSpacesRepository.deleteById(id);
    }

    public boolean isParkingSpaceOccupied(Long id) {
        return parkingSpacesRepository.findById(id)
                .map(parkingSpace -> parkingSpace.isOccupied())
                .orElse(false);
    }

    public List<ParkingSpace> getAvailableParkingSpaces() {
        return parkingSpacesRepository.findAll().stream()
                .filter(parkingSpace -> !parkingSpace.isOccupied() )
                .toList();

    }

    public List<ParkingSpace> getAvailableSlots(SlotTypeEnum slotType) {
        return parkingSpacesRepository.findAvailableSlotsBySlotType(slotType);
    }



}
