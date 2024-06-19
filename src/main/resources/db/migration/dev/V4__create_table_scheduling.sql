CREATE TABLE scheduling (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    barber_id UUID NOT NULL,
    client_id UUID UNIQUE,
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,
    FOREIGN KEY (barber_id) REFERENCES barber(id),
    FOREIGN KEY (client_id) REFERENCES client(id)
);