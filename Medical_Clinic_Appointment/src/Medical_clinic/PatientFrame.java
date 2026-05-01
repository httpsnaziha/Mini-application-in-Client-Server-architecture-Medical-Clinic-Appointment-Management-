package Medical_clinic;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import util.DBConnection;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import util.DBConnection;

public class PatientFrame extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    
    //private Connection connect;
    DBConnection con = new DBConnection();
    //colors
    Color tealGreen = new Color(0, 150, 136);
    Color white     = new Color(255, 255, 255);
    Color mintColor = new Color(232, 245, 242);
    Color labelGray = new Color(100, 120, 115);
    Color tableHead = new Color(240, 244, 243);
    Color dangerRed = new Color(162, 45, 45);
    Color dangerBg  = new Color(252, 235, 235);
    
    private int answer;

    //buttons
    private JButton add_patient;
    private JButton delete_patient;
    private JButton edit_patient;

    //text fields
    private JTextField lastNameField;
    private JTextField firstNameField;
    private JTextField dateBirthField;
    private JTextField phoneField;
    private JTextField addressField;

    //table
    private JTable patientTable;
    private DefaultTableModel tableModel;
    

    private Connection connect;
    private Statement state;
    
    private String originalLastName;
    private String originalFirstName;
    private String originalDob;
    public PatientFrame() {
     //connection to data base
    	connect = DBConnection.getConnection();
    	
       //==================================================
        this.setTitle("Patients");
        this.setSize(1100, 560);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
     
        //panels
        JPanel panel = new JPanel(null);
        panel.setBackground(mintColor);
        this.setContentPane(panel);

        JLabel label = new JLabel("Patients Management");
        label.setFont(new Font("SansSerif", Font.BOLD, 28));
        label.setForeground(tealGreen);
        label.setBounds(20, 10, 500, 40);
        panel.add(label);

        // left white card ===============================================
        JPanel info = new JPanel(null);
        info.setBackground(white);
        info.setBounds(15, 60, 450, 450);
        panel.add(info);

        JLabel cardTitle = new JLabel("PATIENT INFO");
        cardTitle.setFont(new Font("SansSerif", Font.BOLD, 11));
        cardTitle.setForeground(labelGray);
        cardTitle.setBounds(15, 12, 200, 16);
        info.add(cardTitle);

        // last name===============================================================================
        JLabel last_name = new JLabel("Last name");
        last_name.setFont(new Font("SansSerif", Font.PLAIN, 13));
        last_name.setForeground(labelGray);
        last_name.setBounds(15, 38, 200, 16);
        info.add(last_name);
        lastNameField = new JTextField();
        lastNameField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lastNameField.setBounds(15, 55, 410, 30);
        info.add(lastNameField);

        // first name =================================================================
        JLabel first_name = new JLabel("First name");
        first_name.setFont(new Font("SansSerif", Font.PLAIN, 13));
        first_name.setForeground(labelGray);
        first_name.setBounds(15, 98, 200, 16);
        info.add(first_name);
        firstNameField = new JTextField();
        firstNameField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        firstNameField.setBounds(15, 115, 410, 30);
        info.add(firstNameField);

        // date of birth ========================================================
        JLabel date = new JLabel("Date of birth (DD/MM/YYYY)");
        date.setFont(new Font("SansSerif", Font.PLAIN, 13));
        date.setForeground(labelGray);
        date.setBounds(15, 158, 280, 16);
        info.add(date);
        dateBirthField = new JTextField();
        dateBirthField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        dateBirthField.setBounds(15, 175, 410, 30);
        info.add(dateBirthField);

        // phone ==========================================================
        JLabel phone = new JLabel("Phone");
        phone.setFont(new Font("SansSerif", Font.PLAIN, 13));
        phone.setForeground(labelGray);
        phone.setBounds(15, 218, 200, 16);
        info.add(phone);
        phoneField = new JTextField();
        phoneField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        phoneField.setBounds(15, 235, 410, 30);
        info.add(phoneField);

        // address ===================================================
        JLabel lbl5 = new JLabel("Address");
        lbl5.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lbl5.setForeground(labelGray);
        lbl5.setBounds(15, 278, 200, 16);
        info.add(lbl5);
        addressField = new JTextField();
        addressField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        addressField.setBounds(15, 295, 410, 30);
        info.add(addressField);

        // buttons =================================================
        add_patient = new JButton("Add");
        add_patient.setBounds(15, 360, 190, 34);
        add_patient.setBackground(tealGreen);
        add_patient.setForeground(white);
        add_patient.setFont(new Font("SansSerif", Font.BOLD, 14));
        add_patient.setFocusable(false);
        add_patient.addActionListener(this);
        info.add(add_patient);
//====================================================
        edit_patient = new JButton("Edit");
        edit_patient.setBounds(220, 360, 190, 34);
        edit_patient.setBackground(tealGreen);
        edit_patient.setForeground(white);
        edit_patient.setFont(new Font("SansSerif", Font.BOLD, 14));
        edit_patient.setFocusable(false);
        edit_patient.addActionListener(this);
        info.add(edit_patient);
//===================================================================================
        delete_patient = new JButton("Delete");
        delete_patient.setBounds(15, 404, 190, 34);
        delete_patient.setBackground(dangerBg);
        delete_patient.setForeground(dangerRed);
        delete_patient.setFont(new Font("SansSerif", Font.BOLD, 14));
        delete_patient.setFocusable(false);
        delete_patient.addActionListener(this);
        info.add(delete_patient);

        // right table card ============================================================
        JPanel tableCard = new JPanel(null);
        tableCard.setBackground(white);
        tableCard.setBounds(480, 60, 600, 450);
        panel.add(tableCard);

        JLabel tableTitle = new JLabel("PATIENT LIST");
        tableTitle.setFont(new Font("SansSerif", Font.BOLD, 11));
        tableTitle.setForeground(labelGray);
        tableTitle.setBounds(15, 12, 200, 16);
        tableCard.add(tableTitle);

        //table ======================================================================================
        String[] columns = {"Last name", "First name", "Date of birth", "Phone", "Address"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };

        patientTable = new JTable(tableModel);
        patientTable.setRowHeight(28);
        patientTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        patientTable.setBackground(white);
        patientTable.setSelectionBackground(mintColor);
        patientTable.setSelectionForeground(new Color(0, 80, 70));
        patientTable.setShowVerticalLines(false);
        patientTable.setFocusable(true);
        patientTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        patientTable.getTableHeader().setBackground(tableHead);
        patientTable.getTableHeader().setForeground(labelGray);
        patientTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(patientTable);
        scroll.setBounds(15, 35, 568, 400);
        tableCard.add(scroll);

        patientTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = patientTable.getSelectedRow();
                if (row >= 0) {
                    //Save original values to use in edit_patient
                    originalLastName  = (String) tableModel.getValueAt(row, 0);
                    originalFirstName = (String) tableModel.getValueAt(row, 1);
                    originalDob       = (String) tableModel.getValueAt(row, 2);

                    lastNameField.setText(originalLastName);
                    firstNameField.setText(originalFirstName);
                    dateBirthField.setText(originalDob);
                    phoneField.setText((String) tableModel.getValueAt(row, 3));
                    addressField.setText((String) tableModel.getValueAt(row, 4));
                }
            }
        });
        loadPatients();
        this.setVisible(true);
    }
    // load patients infos from the data base
    private void loadPatients() {
        try {
            tableModel.setRowCount(0); // clear table first
            state = connect.createStatement();
            // TO_CHAR converts the date to DD/MM/YYYY so DELETE and UPDATE can use TO_DATE on it later
            ResultSet rs = state.executeQuery(
                "SELECT NAME, SURNAME, TO_CHAR(BIRTH_DATE,'DD/MM/YYYY') AS BIRTH_DATE, PHONE, ADDRESS FROM patient");
            while (rs.next()) {
                String lastName  = rs.getString("NAME");      // NAME  = last name
                String firstName = rs.getString("SURNAME");   // SURNAME = first name
                String dob       = rs.getString("BIRTH_DATE");
                String phone     = rs.getString("PHONE");
                String address   = rs.getString("ADDRESS");
                tableModel.addRow(new Object[]{lastName, firstName, dob, phone, address});
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading patients: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
         
    	//add patient to the data base
        if (e.getSource() == add_patient) {
            try {
				state = connect.createStatement();
        	
                 String last  = lastNameField.getText().trim();
                 String first = firstNameField.getText().trim();
                 String dob   = dateBirthField.getText().trim();
                 String ph    = phoneField.getText().trim();
                 String adr   = addressField.getText().trim();
                //
                 // NAME = last name, SURNAME = first name
                 String sql = "insert into patient (NAME,SURNAME,BIRTH_DATE,PHONE,ADDRESS) values ('"+last+"','"+first+"','"+dob+"','"+ph+"','"+adr+"')";
                 String commit = "commit";
                 state.execute(sql);
                 state.execute(commit);
                 loadPatients();
                 JOptionPane.showMessageDialog(add_patient,"patient inserted!");
                 clearFields();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        }

        //delete patient from the data base
        if (e.getSource() == delete_patient) {
            int row = patientTable.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(null, "Select a patient!"); return; }
            answer = JOptionPane.showConfirmDialog(null, "Are you sure?", "Delete patient", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                try {
                    //Delete from database 
                    String lastName  = (String) tableModel.getValueAt(row, 0);
                    String firstName = (String) tableModel.getValueAt(row, 1);
                    String dob       = (String) tableModel.getValueAt(row, 2);

                    // NAME = last name, SURNAME = first name 
                    String sql = "DELETE FROM patient WHERE NAME='" + lastName
                            + "' AND SURNAME='" + firstName
                            + "' AND BIRTH_DATE='" + dob + "'";
                    state = connect.createStatement();
                    state.execute(sql);
                    state.execute("commit");

                    //load patients from the data base
                    loadPatients();
                    clearFields();
                   
                    JOptionPane.showMessageDialog(null, "Patient deleted!");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
            
       //edit patient from data base
        if (e.getSource() == edit_patient) {
            int row = patientTable.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(null, "Select a patient first!"); return; }

            try {
                state = connect.createStatement();
                String newLast  = lastNameField.getText().trim();
                String newFirst = firstNameField.getText().trim();
                String newDob   = dateBirthField.getText().trim();
                String newPh    = phoneField.getText().trim();
                String newAdr   = addressField.getText().trim();

                
                String sql = "UPDATE patient SET "
                        + "NAME ='"        + newLast  + "', "
                        + "SURNAME ='"     + newFirst + "', "
                        + "BIRTH_DATE ='"  + newDob   + "', "
                        + "PHONE ='"       + newPh    + "', "
                        + "ADDRESS ='"     + newAdr   + "' "
                        + "WHERE NAME ='" + originalLastName  + "' "
                        + "AND SURNAME ='" + originalFirstName + "' "
                        + "AND BIRTH_DATE ='"+ originalDob       + "'";

                state.execute(sql);
                state.execute("commit");

                loadPatients();
                JOptionPane.showMessageDialog(null, "Patient updated!");
                clearFields();
                System.out.println("Button clicked: " + e.getSource());
            } catch (SQLException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error updating patient: " + e1.getMessage());
            }
        }}

    private void clearFields() {
        lastNameField.setText("");
        firstNameField.setText("");
        dateBirthField.setText("");
        phoneField.setText("");
        addressField.setText("");
        patientTable.clearSelection();
    }
}