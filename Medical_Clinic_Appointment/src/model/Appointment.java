package model;

import java.util.Date;

public class Appointment {
    private int id;
    private int patientId;
    private int doctorId;
    private Date date;
    private String time; // Stocké en String pour faciliter l'affichage, converti en Timestamp dans le DAO
    private String status;

    public Appointment(int id, int patientId, int doctorId, Date date, String time, String status) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    // Getters
    public int getId() { return id; }
    public int getPatientId() { return patientId; }
    public int getDoctorId() { return doctorId; }
    public Date getDate() { return date; }
    public String getTime() { return time; }
    public String getStatus() { return status; }

    // Setters (Optionnels mais utiles si vous voulez modifier un objet existant)
    public void setStatus(String status) { this.status = status; }
}