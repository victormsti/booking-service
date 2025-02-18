CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    type VARCHAR(50) NOT NULL CHECK (type IN ('CLIENT', 'PROPERTY_ADMIN'))
);

CREATE TABLE properties (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(20) NOT NULL CHECK (type IN ('HOTEL', 'HOSTEL', 'APARTMENT', 'SHARED_APARTMENT')),
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE rooms (
    id SERIAL PRIMARY KEY,
    property_id INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    type VARCHAR(10) NOT NULL CHECK (type IN ('SINGLE', 'DOUBLE')),
    capacity INT NOT NULL,
    price_per_night DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (property_id) REFERENCES properties(id) ON DELETE CASCADE
);

CREATE TABLE bookings (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    room_id INT NOT NULL,
    status VARCHAR(10) NOT NULL CHECK (status IN ('CONFIRMED', 'CANCELLED', 'PENDING', 'BLOCKED')),
    type VARCHAR(15) NOT NULL CHECK (type IN ('GUEST', 'BLOCK')),
    quantity_of_people INT,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    final_price DECIMAL(10,2),
    payment_status VARCHAR(10) CHECK (payment_status IN ('PENDING', 'PAID', 'REFUNDED')),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE
);

CREATE TABLE guests (
    id SERIAL PRIMARY KEY,
    booking_id INT NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    main_guest BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE CASCADE
);

-- TODO create a m:n table for property_admins
-- password: 123456
INSERT INTO users (username, password, type, email)
VALUES ('test_user', '$2a$12$OJybB6PGaqCA7N210TMOY.wMNoQovhtWKNvQ34GP1VWcqaaY7MLA6', 'CLIENT', 'user@example.com');

-- password: admin
INSERT INTO users (username, password, type, email)
VALUES ('property_admin', '$2a$12$tOEj1qSUERoH3KBHyv17oOd79xx.ihPdC2tFj9kh/c.oNThh0l/8S', 'PROPERTY_ADMIN', 'admin@example.com');

INSERT INTO properties (name, type, user_id) VALUES
('Hotel Paradise', 'HOTEL', 2),
('Hostel Central', 'HOSTEL', 2),
('Cozy Apartment', 'APARTMENT', 2),
('Shared Living Space', 'SHARED_APARTMENT', 2);

INSERT INTO rooms (property_id, name, type, capacity, price_per_night) VALUES
(1, 'Deluxe Suite', 'DOUBLE', 2, 150.00),
(1, 'Standard Room', 'SINGLE', 1, 80.00),
(2, 'Bunk Bed', 'SINGLE', 1, 40.00),
(3, 'Entire Apartment', 'DOUBLE', 4, 200.00);

INSERT INTO bookings (user_id, room_id, status, type, quantity_of_people, check_in_date, check_out_date, final_price, payment_status)
VALUES (1, 1, 'CONFIRMED', 'GUEST', 2, '2025-04-01', '2025-04-05', 600.00, 'PAID');

INSERT INTO guests (booking_id, first_name, last_name, birth_date, main_guest)
VALUES
    (1, 'John', 'Doe', '1990-05-15', TRUE),
    (1, 'Jane', 'Doe', '1992-08-21', FALSE);