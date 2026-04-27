package Maintest;

import dao.*;
import model.*;
import java.util.Date;

public class MainTest {
    public static void main(String[] args) {
        PatientDAO patientDao = new PatientDAO();
        DoctorDAO doctorDao = new DoctorDAO();
        AppointmentDAO appDao = new AppointmentDAO();

        System.out.println("=== STARTING CLEAN TEST ===");

        // 1. On utilise des IDs totalement nouveaux (ex: 888 et 999)
        int newPatientId = 888;
        int newDoctorId = 999;
        int newAppId = 7777;

        // 2. Ajouter le Patient
        Patient p = new Patient(newPatientId, "Bouhired", "Djamila", new Date(), "0555001122", "Alger");
        patientDao.addPatient(p);

        // 3. Ajouter le Docteur
        Doctor d = new Doctor(newDoctorId, "Benmhidi", "Larbi", "Surgeon", "0666001122");
        doctorDao.addDoctor(d);

        // 4. Prendre le rendez-vous (On utilise EXACTEMENT les mêmes IDs que dessus)
        // C'est ici que l'erreur ORA-02291 se produit si les IDs ne correspondent pas
        Appointment app = new Appointment(newAppId, newPatientId, newDoctorId, new Date(), "10:00", "Scheduled");
        
        System.out.println("Booking appointment with IDs: Patient=" + newPatientId + ", Doctor=" + newDoctorId);
        appDao.bookAppointment(app);

        System.out.println("=== GLOBAL TEST FINISHED ===");
    }
}