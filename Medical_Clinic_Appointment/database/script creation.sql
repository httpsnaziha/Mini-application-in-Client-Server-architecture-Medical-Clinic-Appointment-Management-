-- 1. Nettoyage (On efface tout pour recommencer proprement)
DROP TABLE Appointment CASCADE CONSTRAINTS;
DROP TABLE Patient CASCADE CONSTRAINTS;
DROP TABLE Doctor CASCADE CONSTRAINTS;
DROP SEQUENCE seq_patient;
DROP SEQUENCE seq_doctor;
DROP SEQUENCE seq_app;

-- 2. Table Patient + Auto-ID
CREATE TABLE Patient (
    PATIENT_ID NUMBER PRIMARY KEY,
    NAME VARCHAR2(100),
    SURNAME VARCHAR2(100),
    BIRTH_DATE DATE,
    PHONE VARCHAR2(20),
    ADDRESS VARCHAR2(200)
);
CREATE SEQUENCE seq_patient START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER trg_patient_id BEFORE INSERT ON Patient FOR EACH ROW
BEGIN SELECT seq_patient.NEXTVAL INTO :new.PATIENT_ID FROM dual; END;
/

-- 3. Table Doctor + Auto-ID
CREATE TABLE Doctor (
    DOCTOR_ID NUMBER PRIMARY KEY,
    NAME VARCHAR2(100),
    SURNAME VARCHAR2(100),
    SPECIALTY VARCHAR2(100),
    PHONE VARCHAR2(20)
);
CREATE SEQUENCE seq_doctor START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER trg_doctor_id BEFORE INSERT ON Doctor FOR EACH ROW
BEGIN SELECT seq_doctor.NEXTVAL INTO :new.DOCTOR_ID FROM dual; END;
/

<<<<<<< HEAD
-- 4. Create Appointment Table 
=======
-- 4. Table Appointment + Auto-ID
>>>>>>> branch 'main' of https://github.com/nour-m29/mini-application-in-client-server-architecture-medical-clinic-appointment-management-
CREATE TABLE Appointment (
    APP_ID NUMBER PRIMARY KEY,
    APP_DATE DATE,
    APP_TIME VARCHAR2(10),
    STATUS VARCHAR2(20) DEFAULT 'Scheduled',
    PATIENT_ID NUMBER,
    DOCTOR_ID NUMBER,
    CONSTRAINT fk_app_patient FOREIGN KEY (PATIENT_ID) REFERENCES Patient(PATIENT_ID),
    CONSTRAINT fk_app_doctor FOREIGN KEY (DOCTOR_ID) REFERENCES Doctor(DOCTOR_ID)
);
CREATE SEQUENCE seq_app START WITH 1 INCREMENT BY 1;
CREATE OR REPLACE TRIGGER trg_app_id BEFORE INSERT ON Appointment FOR EACH ROW
BEGIN SELECT seq_app.NEXTVAL INTO :new.APP_ID FROM dual; END;
/

-- 5. Données de Test (Sans IDs)
INSERT INTO Patient (NAME, SURNAME, BIRTH_DATE, PHONE, ADDRESS) 
VALUES ('Ahmed', 'Slimani', TO_DATE('2003-05-09', 'YYYY-MM-DD'), '0555040674', 'Kouba');

INSERT INTO Doctor (NAME, SURNAME, SPECIALTY, PHONE)
VALUES ('Islem', 'Hamdi', 'Cardiologist', '666112233');

COMMIT;

-- 6. Vérification finale
SELECT * FROM Patient;
SELECT * FROM Doctor;
SELECT * FROM Appointment;