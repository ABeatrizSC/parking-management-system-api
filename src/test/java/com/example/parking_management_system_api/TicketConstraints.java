package com.example.parking_management_system_api;

import com.example.parking_management_system_api.entities.ParkingSpace;
import com.example.parking_management_system_api.entities.Ticket;
import com.example.parking_management_system_api.entities.Vehicle;
import com.example.parking_management_system_api.models.SlotTypeEnum;
import com.example.parking_management_system_api.models.VehicleCategoryEnum;
import com.example.parking_management_system_api.models.VehicleTypeEnum;
import com.example.parking_management_system_api.web.dto.TicketCreateDto;
import com.example.parking_management_system_api.web.dto.TicketResponseDto;

import java.time.LocalTime;

public class TicketConstraints {

    //VEHICLES
    public static final Vehicle VEHICLE1 = new Vehicle(1L, "ABC-1234", VehicleCategoryEnum.SEPARATED, VehicleTypeEnum.PASSENGER_CAR, false);
    public static final Vehicle VEHICLE12 = new Vehicle(2L, "ABC-1235", VehicleCategoryEnum.SEPARATED, VehicleTypeEnum.MOTORCYCLE, false);
    public static final Vehicle VEHICLE2 = new Vehicle(3L, "ABC-1236", VehicleCategoryEnum.MONTHLY_PAYER, VehicleTypeEnum.PASSENGER_CAR, Boolean.TRUE);
    public static final Vehicle VEHICLE22 = new Vehicle(4L, "ABC-1237", VehicleCategoryEnum.MONTHLY_PAYER, VehicleTypeEnum.MOTORCYCLE, Boolean.TRUE);
    public static final Vehicle VEHICLE3 = new Vehicle(5L, "ABC-1238", VehicleCategoryEnum.DELIVERY_TRUCK, VehicleTypeEnum.DELIVERY_TRUCK, false);
    public static final Vehicle VEHICLE4 = new Vehicle(6L, "ABC-1239", VehicleCategoryEnum.PUBLIC_SERVICE, VehicleTypeEnum.PUBLIC_SERVICE, false);

    //TICKETCREATEDTO
    public static final TicketCreateDto DTO = new TicketCreateDto("ABC-1234", VehicleCategoryEnum.MONTHLY_PAYER);
    public static final TicketCreateDto DTO1 = new TicketCreateDto("ABC-1236", VehicleCategoryEnum.MONTHLY_PAYER);
    public static final TicketCreateDto DTO2 = new TicketCreateDto("ABC-1235", VehicleCategoryEnum.SEPARATED);
    public static final TicketCreateDto DTO3 = new TicketCreateDto("ABC-1237", VehicleCategoryEnum.SEPARATED);
    public static final TicketCreateDto DTO4 = new TicketCreateDto("ABC-1238", VehicleCategoryEnum.DELIVERY_TRUCK);
    public static final TicketCreateDto DTO5 = new TicketCreateDto("ABC-1239", VehicleCategoryEnum.PUBLIC_SERVICE);

    //TICKETS
    public static final TicketResponseDto TICKET = new TicketResponseDto(1L, VEHICLE1, true, LocalTime.now(), LocalTime.now(), 1, 10, 5.00, "201, 202");
    public static final Ticket TICKET2 = new Ticket(2L, LocalTime.now(), LocalTime.now(), 5.00, true, 5, 10, "201", VEHICLE12);
    public static final Ticket TICKET3 = new Ticket(3L, LocalTime.now(), LocalTime.now(), 0.00, true, 1, 10, "1, 2", VEHICLE2);
    public static final Ticket TICKET4 = new Ticket(4L, LocalTime.now(), LocalTime.now(), 0.00, true, 5, 10, "1", VEHICLE22);
    public static final Ticket TICKET5 = new Ticket(5L, LocalTime.now(), LocalTime.now(), 5.00, true, 1, 10, "201, 202, 203, 204", VEHICLE3);
    public static final Ticket TICKET6 = new Ticket(6L, LocalTime.now(), LocalTime.now(), 5.00, true, 1, 10, "", VEHICLE4);

    //SPACES
    public static final ParkingSpace SPACE1 = new ParkingSpace(1L, 201, false, SlotTypeEnum.CASUAL, null);
    public static final ParkingSpace SPACE2 = new ParkingSpace(2L, 202, false, SlotTypeEnum.CASUAL, null);
    public static final ParkingSpace SPACE3 = new ParkingSpace(3L, 203, false, SlotTypeEnum.CASUAL, null);
    public static final ParkingSpace SPACE4 = new ParkingSpace(4L, 204, false, SlotTypeEnum.CASUAL, null);
    public static final ParkingSpace SPACE5 = new ParkingSpace(5L, 205, false, SlotTypeEnum.CASUAL, null);
    public static final ParkingSpace SPACE6 = new ParkingSpace(6L, 1, false, SlotTypeEnum.MONTHLY, null);
    public static final ParkingSpace SPACE7 = new ParkingSpace(7L, 2, false, SlotTypeEnum.MONTHLY, null);
    public static final ParkingSpace SPACE8 = new ParkingSpace(8L, 3, false, SlotTypeEnum.MONTHLY, null);
    public static final ParkingSpace SPACE9 = new ParkingSpace(9L, 4, false, SlotTypeEnum.MONTHLY, null);
    public static final ParkingSpace SPACE10 = new ParkingSpace(10L, 5, false, SlotTypeEnum.MONTHLY, null);
}
