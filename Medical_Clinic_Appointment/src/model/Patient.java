package model;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Patient {
    private int id;
    private String name;
    private String surname;
    private Date birthDate;
    private String phone;
    private String address;

    public Patient(int id, String name, String surname, Date birthDate, String phone, String address) {
        this.id = id; this.name = name; this.surname = surname;
        this.birthDate = birthDate; this.phone = phone; this.address = address;
    }

    public Patient(String last, String first, String dob, String ph, String adr) {
    	this.surname = last;
        this.name = first;
        this.phone = ph;
        this.address = adr;
        
        // On transforme le texte "23/04/2005" en vraie Date Java
        try {
            this.birthDate = new SimpleDateFormat("dd/MM/yyyy").parse(dob);
        } catch (Exception e) {
            System.err.println("Format date invalide : " + dob);
            this.birthDate = null; 
        }
    }
	

	public int getId() { return id; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public Date getBirthDate() { return birthDate; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
}