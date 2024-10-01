INSERT INTO vehicles (license_plate, category, access_type, registered) VALUES
('ABC1234', 'SEPARATED', 'PASSENGER_CAR', FALSE),
('MPR1234', 'MONTHLY_PAYER', 'PASSENGER_CAR', TRUE),
('DEF5678', 'SEPARATED', 'MOTORCYCLE', FALSE),
('GHI9101', 'DELIVERY_TRUCK', 'DELIVERY_TRUCK', FALSE),
('JKL2345', 'PUBLIC_SERVICE', 'PUBLIC_SERVICE', FALSE);
INSERT INTO parking_spaces (number, is_occupied, slot_Type, vehicles_id) VALUES
                                                                           (1, false, 'MONTHLY', NULL),
                                                                           (2, false, 'MONTHLY', NULL),
                                                                           (3, false, 'MONTHLY', NULL),
                                                                           (4, false, 'MONTHLY', NULL),
                                                                           (5, false, 'MONTHLY', NULL),
                                                                           (6, false, 'CASUAL', NULL),
                                                                           (7, false, 'CASUAL', NULL),
                                                                           (8, false, 'CASUAL', NULL),
                                                                           (9, false, 'CASUAL', NULL),
                                                                           (10, false, 'CASUAL', NULL),
