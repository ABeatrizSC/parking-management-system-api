INSERT INTO vehicles (license_plate, category, access_type, registered) VALUES
('ABC1234', 'SEPARATED', 'PASSENGER_CAR', FALSE),
('MPR1234', 'MONTHLY_PAYER', 'PASSENGER_CAR', TRUE),
('DEF5678', 'SEPARATED', 'MOTORCYCLE', FALSE),
('GHI9101', 'DELIVERY_TRUCK', 'DELIVERY_TRUCK', FALSE),
('JKL2345', 'PUBLIC_SERVICE', 'PUBLIC_SERVICE', FALSE);
SET @rownum = 0;
INSERT INTO parking_spaces (number, is_occupied, slot_type, vehicles_id)
SELECT @rownum := @rownum + 1 AS seq, false, 'MONTHLY', NULL
FROM information_schema.tables
LIMIT 200;

SET @rownum = 200;
INSERT INTO parking_spaces (number, is_occupied, slot_type, vehicles_id)
SELECT @rownum := @rownum + 1 AS seq, false, 'CASUAL', NULL
FROM information_schema.tables
LIMIT 300;
INSERT INTO tickets (start_hour, finish_hour, total_value, parked, entrance_gate, exit_gate, parking_spaces, vehicles_id) VALUES
('30/09/2024 14:26:12', NULL, NULL, TRUE, 2, NULL, "201, 202", 1);
UPDATE parking_spaces SET is_occupied=TRUE WHERE id=256;
UPDATE parking_spaces SET is_occupied=TRUE WHERE id=257;

