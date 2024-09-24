package com.example.parking_management_system_api.util;

public class ParkingUtil {

    private final double BASIC_PAYMENT = 5.00;
    private final double PRICE_PER_MINUTE = 0.10;

    //    public void calculateTotalValue() {
//        Duration duration = Duration.between(startHour, finishHour);
//        long minutesParked = duration.toMinutes();
//
//        int slotsOccupied = vehicle.getSlotSize();
//
//        double total = minutesParked * PRICE_PER_MINUTE * slotsOccupied;
//
//        if (total <= BASIC_PAYMENT) {
//            total = BASIC_PAYMENT;
//        }
//
//        this.totalValue = total;
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("==============================\n")
//                .append("TICKET NUMBER #").append(id).append(":\n")
//                .append("Vehicle ID: ").append(vehicle.getId()).append("\n")
//                .append("Start Hour: ").append(startHour).append("\n")
//                .append("Parking Spaces: ").append(parkingSpaces).append("\n")
//                .append("Entrance Gate: ").append(vehicle.getEntranceGate()).append("\n");
//
//        if (finishHour != null && vehicle.getExitGate() != null && totalValue != null) {
//            sb.append("Finish Hour: ").append(finishHour).append("\n")
//                    .append("Exit Gate: ").append(vehicle.getExitGate()).append("\n")
//                    .append("Total Value: ").append(totalValue).append("\n");
//        } else {
//            sb.append("Finish Hour: -\n")
//                    .append("Exit Gate: -\n")
//                    .append("Total value: -\n")
//                    .append(ANSI_RED + "Warning: You will need to provide your ticket number at the exit.\n" + ANSI_RESET);
//        }
//
//        sb.append("==============================");
//        return sb.toString();
//    }
}
