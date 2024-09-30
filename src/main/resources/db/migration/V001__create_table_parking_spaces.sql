CREATE TABLE vehicles (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    license_plate VARCHAR(100) NOT NULL,
    category VARCHAR(100) NOT NULL,
    access_type VARCHAR(50) NOT NULL,
    registered TINYINT(1) NULL
);

CREATE TABLE tickets (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    start_hour VARCHAR(50) NULL,
    finish_hour VARCHAR(50) NULL,
    total_value DOUBLE NULL,
    parked TINYINT(1) NULL,
    entrance_gate BIGINT NULL,
    exit_gate BIGINT NULL,
    parking_spaces VARCHAR(100) NULL,
    vehicles_id BIGINT
);

CREATE TABLE parking_spaces (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    number BIGINT NOT NULL,
    is_occupied TINYINT(1) NOT NULL,
    slot_type VARCHAR(200) NOT NULL,
    vehicles_id BIGINT
);

ALTER TABLE parking_spaces
    ADD CONSTRAINT fk_parking_spaces_vehicles_id
    FOREIGN KEY (vehicles_id)
    REFERENCES vehicles(id);

ALTER TABLE tickets
    ADD CONSTRAINT fk_tickets_vehicles_id
    FOREIGN KEY (vehicles_id)
    REFERENCES vehicles(id);