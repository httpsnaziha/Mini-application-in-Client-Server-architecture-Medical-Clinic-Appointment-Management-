package dao;

import java.sql.*;
import util.DBConnection;

public class AppointmentDAO {

    /**
     * Ajoute un rendez-vous en cherchant les IDs à partir des noms saisis
     */
    public boolean addAppointment(String patientFullName, String doctorFullName, String date, String time, String status) {
        
        // 1. Requêtes pour retrouver les IDs (on cherche Nom + Prénom ou Prénom + Nom)
        String findPatientSQL = "SELECT PATIENT_ID FROM PATIENT WHERE (NAME || ' ' || SURNAME) = ? OR (SURNAME || ' ' || NAME) = ?";
        String findDoctorSQL = "SELECT DOCTOR_ID FROM DOCTOR WHERE (NAME || ' ' || SURNAME) = ? OR (SURNAME || ' ' || NAME) = ?";
        
        // 2. Requête d'insertion (L'ID APP_ID est géré par le TRIGGER dans Oracle)
        String insertSQL = "INSERT INTO Appointment (APP_DATE, APP_TIME, STATUS, PATIENT_ID, DOCTOR_ID) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection()) {
            if (con == null) return false;

            // --- ÉTAPE A : Récupérer l'ID du Patient ---
            int patientId = -1;
            try (PreparedStatement psP = con.prepareStatement(findPatientSQL)) {
                psP.setString(1, patientFullName);
                psP.setString(2, patientFullName);
                ResultSet rsP = psP.executeQuery();
                if (rsP.next()) {
                    patientId = rsP.getInt("PATIENT_ID");
                }
            }

            // --- ÉTAPE B : Récupérer l'ID du Docteur ---
            int doctorId = -1;
            try (PreparedStatement psD = con.prepareStatement(findDoctorSQL)) {
                psD.setString(1, doctorFullName);
                psD.setString(2, doctorFullName);
                ResultSet rsD = psD.executeQuery();
                if (rsD.next()) {
                    doctorId = rsD.getInt("DOCTOR_ID");
                }
            }

            // --- ÉTAPE C : Insertion si les deux IDs ont été trouvés ---
            if (patientId != -1 && doctorId != -1) {
                try (PreparedStatement psI = con.prepareStatement(insertSQL)) {
                    // Conversion de la date (ex: "27/04/2026" -> "2026-04-27")
                    psI.setDate(1, java.sql.Date.valueOf(convertDate(date))); 
                    psI.setString(2, time);   // Format "HH:MM"
                    psI.setString(3, status); // "Scheduled" par défaut
                    psI.setInt(4, patientId);
                    psI.setInt(5, doctorId);
                    
                    psI.executeUpdate();
                    System.out.println("Rendez-vous ajouté avec succès !");
                    return true;
                }
            } else {
                System.err.println("Erreur : Patient ou Docteur introuvable. Vérifiez l'orthographe !");
                return false;
            }

        } catch (Exception e) {
            System.err.println("Erreur technique : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Utilitaire pour transformer "DD/MM/YYYY" en "YYYY-MM-DD" pour SQL
     */
    private String convertDate(String dateStr) {
        try {
            String[] parts = dateStr.split("/");
            return parts[2] + "-" + parts[1] + "-" + parts[0];
        } catch (Exception e) {
            return "2026-01-01"; // Date par défaut en cas d'erreur de saisie
        }
    }
}