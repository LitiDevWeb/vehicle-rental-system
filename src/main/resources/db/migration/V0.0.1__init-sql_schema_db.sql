CREATE TABLE xxx (
                        client_id SERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        phone_number VARCHAR(20),
                        has_unpaid_debt BOOLEAN DEFAULT FALSE,
                        has_unpaid_cautions BOOLEAN DEFAULT FALSE,
                        created_at TIMESTAMP NOT NULL
);

CREATE TABLE vehicle (
                         vehicle_id SERIAL PRIMARY KEY,
                         status VARCHAR(50) NOT NULL,
                         km DOUBLE PRECISION,
                         created_at TIMESTAMP NOT NULL
);

CREATE TABLE rental (
                        rental_id SERIAL PRIMARY KEY,
                        start_date TIMESTAMP NOT NULL,
                        end_date TIMESTAMP NOT NULL,
                        status VARCHAR(50) NOT NULL,
                        created_at TIMESTAMP NOT NULL,
                        client_id INT NOT NULL,
                        vehicle_id INT NOT NULL,
                        CONSTRAINT fk_rental_client FOREIGN KEY (client_id) REFERENCES client(client_id),
                        CONSTRAINT fk_rental_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicle(vehicle_id)
);

CREATE TABLE inspection (
                            inspection_id SERIAL PRIMARY KEY,
                            status VARCHAR(50) NOT NULL,
                            created_at TIMESTAMP NOT NULL,
                            vehicle_id INT NOT NULL,
                            CONSTRAINT fk_inspection_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicle(vehicle_id)
);
