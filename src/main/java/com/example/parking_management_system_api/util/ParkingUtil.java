package com.example.parking_management_system_api.util;

import com.example.parking_management_system_api.entities.Vehicle;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Optional;

public class ParkingUtil {

    public double calculateTotalValue(LocalTime startHour, LocalTime finishHour, Vehicle vehicle) {
        Duration duration = Duration.between(startHour, finishHour);
        long minutesParked = duration.toMinutes();
        int slotsOccupied = vehicle.getSlotSize();
            double PRICE_PER_MINUTE = 0.10;
            double total = minutesParked * PRICE_PER_MINUTE * slotsOccupied;
            double BASIC_PAYMENT = 5.00;
            if (total <= BASIC_PAYMENT) {
            total = BASIC_PAYMENT;
        }
        return total;
    }
}
