-- 1. Clean up existing tables (important to do it in this order because of constraints)
DROP TABLE Appointment;
DROP TABLE Patient;
DROP TABLE Doctor;

-- 2. Create Patient Table
CREATE TABLE Patient (
    patient_id NUMBER PRIMARY KEY,
    name VARCHAR2(25),
    surname VARCHAR2(25),
    birth_date DATE,
    phone NUMBER,
    address VARCHAR2(50)
);

-- 3. Create Doctor Table
CREATE TABLE Doctor (
    doctor_id NUMBER PRIMARY KEY,
    name VARCHAR2(25),
    surname VARCHAR2(25),
    specialty VARCHAR2(30),
    phone NUMBER
);

-- 4. Create Appointment Table 
CREATE TABLE Appointment (
    appointment_id NUMBER PRIMARY KEY,
    app_date DATE,
    app_time TIMESTAMP,
    status VARCHAR2(20),
    patient_id NUMBER,
    doctor_id NUMBER,
    CONSTRAINT fk_app_patient FOREIGN KEY (patient_id) REFERENCES Patient(patient_id),
    CONSTRAINT fk_app_doctor FOREIGN KEY (doctor_id) REFERENCES Doctor(doctor_id),
    CONSTRAINT check_status CHECK (status IN ('Scheduled', 'Cancelled', 'Completed'))
);

-- 5. Insert Sample Data
INSERT INTO Patient VALUES (100, 'Slimani', 'Ahmed', TO_DATE('2003-05-09', 'YYYY-MM-DD'), 0555040674, 'Kouba');
INSERT INTO Doctor VALUES (213, 'Hamdi', 'Islem', 'Cardiologist', 0666112233);
INSERT INTO Appointment VALUES (1900, TO_DATE('2026-02-13', 'YYYY-MM-DD'), TO_TIMESTAMP('2026-02-13 13:30:00', 'YYYY-MM-DD HH24:MI:SS'), 'Scheduled', 100, 213);

COMMIT;

-- 6. Verify Data
SELECT * FROM Patient;
SELECT * FROM Doctor;
SELECT * FROM Appointment;