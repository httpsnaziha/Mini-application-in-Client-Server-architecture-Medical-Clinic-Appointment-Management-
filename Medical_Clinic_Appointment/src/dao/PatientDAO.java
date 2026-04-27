package dao;

import java.sql.*;
import model.Patient;
import util.DBConnection;
public class PatientDAO {

    // Ajouter un patient
	public boolean addPatient(Patient p) {
	    // 1. On change un peu le SQL pour laisser Oracle gérer l'ID si possible, 
	    // ou alors assure-toi que p.getId() n'est pas 0.
	    String sql = "INSERT INTO PATIENT (NAME, SURNAME, BIRTH_DATE, PHONE, ADDRESS) VALUES ( ?, ?, ?, ?, ?)";
	    
	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	         // Vérifie que l'ID est généré quelque part !
	        ps.setString(1, p.getName());
	        ps.setString(2, p.getSurname());

	        // --- CORRECTION DE LA DATE ---
	        if (p.getBirthDate() != null) {
	            ps.setDate(3, new java.sql.Date(p.getBirthDate().getTime()));
	        } else {
	            // Si la date est null, on envoie la date du jour ou null à la base
	            ps.setNull(3, java.sql.Types.DATE); 
	            // Ou pour tester : ps.setDate(4, new java.sql.Date(System.currentTimeMillis()));
	        }

	        // --- CORRECTION DU PHONE ---
	        // Utilise p.getPhone() directement si c'est une String
	        ps.setString(4, p.getPhone()); 

	        ps.setString(5, p.getAddress());

	        ps.executeUpdate();
	        return true;
	    } catch (Exception e) {
	        System.err.println("Error adding patient: " + e.getMessage());
	        return false;
	    }
	}

    // Supprimer un patient
    public void deletePatient(int id) {
        String sql = "DELETE FROM Patient WHERE patient_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Patient deleted!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}