package model;

public class Doctor {
    private int id;
    private String name, surname, specialty, phone;

    public Doctor(int id, String name, String surname, String specialty, String phone) {
        this.id = id; this.name = name; this.surname = surname;
        this.specialty = specialty; this.phone = phone;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getSpecialty() { return specialty; }
    public String getPhone() { return phone; }
}