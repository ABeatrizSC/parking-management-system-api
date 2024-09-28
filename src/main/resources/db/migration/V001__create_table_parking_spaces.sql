CREATE TABLE tickets (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    startHour TIME NULL,
    finishHour TIME NULL,
    totalValue DOUBLE NULL,
    parked TINYINT(1) NULL,
    entrance_gate BIGINT NULL,
    exit_gate BIGINT NULL,
    parking_spaces VARCHAR(100) NULL
);

CREATE TABLE vehicles (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    license_plate VARCHAR(100) NOT NULL,
    category VARCHAR(100) NOT NULL,
    access_type VARCHAR(50) NOT NULL,
    tickets_id BIGINT NULL,
    registered TINYINT(1) NULL
);

CREATE TABLE parking_spaces (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    number BIGINT NOT NULL,
    isOccupied TINYINT(1) NOT NULL,
    slotType VARCHAR(200) NOT NULL,
    vehicles_id BIGINT
);

ALTER TABLE parking_spaces
    ADD CONSTRAINT fk_vehicle_id
    FOREIGN KEY (vehicles_id)
    REFERENCES vehicles(id);