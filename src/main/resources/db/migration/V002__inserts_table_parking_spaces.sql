INSERT INTO parking_spaces (isOccupied, number, slotType, vehicles_id) VALUES (0, 1, 1, null);
INSERT INTO vehicles (license_plate, category, access_type, registered) VALUES
('ABC1234', 'SIMPLE', 'PASSENGER_CAR', FALSE),
('DEF5678', 'SIMPLE', 'MOTORCYCLE', FALSE),
('GHI9101', 'SIMPLE', 'DELIVERY_TRUCK', FALSE),
('JKL2345', 'SIMPLE', 'PUBLIC_SERVICE', FALSE);
INSERT INTO parking_spaces (number, isOccupied, slotType, vehicles_id) VALUES
(1, false, 'MONTHLY', NULL),
(2, false, 'MONTHLY', NULL),
(3, false, 'CASUAL', NULL),
(4, false, 'CASUAL', NULL),
(5, false, 'MONTHLY', NULL),
(6, false, 'MONTHLY', NULL),
(7, false, 'CASUAL', NULL),
(8, false, 'CASUAL', NULL),
(9, false, 'MONTHLY', NULL),
(10, false, 'MONTHLY', NULL);