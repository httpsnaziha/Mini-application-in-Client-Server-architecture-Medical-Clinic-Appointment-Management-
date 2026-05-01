package Medical_clinic;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import util.DBConnection;

public class DoctorFrame extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

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
    private JButton add_doctor;
    private JButton delete_doctor;
    private JButton edit_doctor;

    //text fields
    private JTextField lastNameField;
    private JTextField firstNameField;
    private JTextField phoneField;
    private JComboBox<String> specialtyCombo;

    //table
    private JTable doctorTable;
    private DefaultTableModel tableModel;

    private String[] specialties = {
        "General Practitioner", "Cardiologist", "Dermatologist",
        "Neurologist", "Pediatrician", "Gynecologist",
        "Ophthalmologist", "Orthopedist", "Psychiatrist", "Radiologist"
    };

    //objet of database connection
    private Connection connect;
    private Statement state;

    // save original values to use in edit WHERE clause
    private String originalLastName;
    private String originalFirstName;

    public DoctorFrame() {
        //connect to the data base
        connect = DBConnection.getConnection();

        //=====================================================================
        this.setTitle("Doctors");
        this.setSize(1100, 510);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        JPanel panel = new JPanel(null);
        panel.setBackground(mintColor);
        this.setContentPane(panel);

        // header =========================================================
        JLabel label = new JLabel("Doctors Management");
        label.setFont(new Font("SansSerif", Font.BOLD, 28));
        label.setForeground(tealGreen);
        label.setBounds(20, 10, 500, 40);
        panel.add(label);

        // left white card ================================================
        JPanel info = new JPanel(null);
        info.setBackground(white);
        info.setBounds(15, 60, 450, 390);
        panel.add(info);

        JLabel title = new JLabel("DOCTOR INFO");
        title.setFont(new Font("SansSerif", Font.BOLD, 11));
        title.setForeground(labelGray);
        title.setBounds(15, 12, 200, 16);
        info.add(title);

        // last name ================================================================
        JLabel last_name = new JLabel("Last name");
        last_name.setFont(new Font("SansSerif", Font.PLAIN, 13));
        last_name.setForeground(labelGray);
        last_name.setBounds(15, 38, 200, 16);
        info.add(last_name);
        lastNameField = new JTextField();
        lastNameField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lastNameField.setBounds(15, 55, 410, 30);
        info.add(lastNameField);

        // first name =====================================================
        JLabel first_Name = new JLabel("First name");
        first_Name.setFont(new Font("SansSerif", Font.PLAIN, 13));
        first_Name.setForeground(labelGray);
        first_Name.setBounds(15, 98, 200, 16);
        info.add(first_Name);
        firstNameField = new JTextField();
        firstNameField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        firstNameField.setBounds(15, 115, 410, 30);
        info.add(firstNameField);

        // speciality ==========================================================
        JLabel spec = new JLabel("Speciality");
        spec.setFont(new Font("SansSerif", Font.PLAIN, 13));
        spec.setForeground(labelGray);
        spec.setBounds(15, 158, 200, 16);
        info.add(spec);
        specialtyCombo = new JComboBox<>(specialties);
        specialtyCombo.setBounds(15, 175, 410, 30);
        specialtyCombo.setBackground(white);
        specialtyCombo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        specialtyCombo.setFocusable(false);
        info.add(specialtyCombo);

        // phone =================================================================
        JLabel phone = new JLabel("Phone");
        phone.setFont(new Font("SansSerif", Font.PLAIN, 13));
        phone.setForeground(labelGray);
        phone.setBounds(15, 218, 200, 16);
        info.add(phone);
        phoneField = new JTextField();
        phoneField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        phoneField.setBounds(15, 235, 410, 30);
        info.add(phoneField);

        // buttons =====================================================================
        add_doctor = new JButton("Add");
        add_doctor.setBounds(15, 300, 190, 34);
        add_doctor.setBackground(tealGreen);
        add_doctor.setForeground(white);
        add_doctor.setFont(new Font("SansSerif", Font.BOLD, 14));
        add_doctor.setFocusable(false);
        add_doctor.addActionListener(this);
        info.add(add_doctor);

        edit_doctor = new JButton("Edit");
        edit_doctor.setBounds(220, 300, 190, 34);
        edit_doctor.setBackground(tealGreen);
        edit_doctor.setForeground(white);
        edit_doctor.setFont(new Font("SansSerif", Font.BOLD, 14));
        edit_doctor.setFocusable(false);
        edit_doctor.addActionListener(this);
        info.add(edit_doctor);

        delete_doctor = new JButton("Delete");
        delete_doctor.setBounds(15, 344, 190, 34);
        delete_doctor.setBackground(dangerBg);
        delete_doctor.setForeground(dangerRed);
        delete_doctor.setFont(new Font("SansSerif", Font.BOLD, 14));
        delete_doctor.setFocusable(false);
        delete_doctor.addActionListener(this);
        info.add(delete_doctor);

        //table ====================================================================
        JPanel tableCard = new JPanel(null);
        tableCard.setBackground(white);
        tableCard.setBounds(480, 60, 600, 390);
        panel.add(tableCard);

        JLabel tableTitle = new JLabel("DOCTOR LIST");
        tableTitle.setFont(new Font("SansSerif", Font.BOLD, 11));
        tableTitle.setForeground(labelGray);
        tableTitle.setBounds(15, 12, 200, 16);
        tableCard.add(tableTitle);

        String[] columns = {"Last name", "First name", "Speciality", "Phone"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };

        doctorTable = new JTable(tableModel);
        doctorTable.setRowHeight(28);
        doctorTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        doctorTable.setBackground(white);
        doctorTable.setSelectionBackground(mintColor);
        doctorTable.setSelectionForeground(new Color(0, 80, 70));
        doctorTable.setShowVerticalLines(false);
        doctorTable.setFocusable(true);
        doctorTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        doctorTable.getTableHeader().setBackground(tableHead);
        doctorTable.getTableHeader().setForeground(labelGray);
        doctorTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(doctorTable);
        scroll.setBounds(15, 35, 568, 340);
        tableCard.add(scroll);

        doctorTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = doctorTable.getSelectedRow();
                if (row >= 0) {
                    //Save original values to use in edit WHERE clause
                    originalLastName  = (String) tableModel.getValueAt(row, 0);
                    originalFirstName = (String) tableModel.getValueAt(row, 1);

                    lastNameField.setText(originalLastName);
                    firstNameField.setText(originalFirstName);
                    specialtyCombo.setSelectedItem(tableModel.getValueAt(row, 2));
                    phoneField.setText((String) tableModel.getValueAt(row, 3));
                }
            }
        });

        loadDoctors();
        this.setVisible(true);
    }

    // load doctors infos from the data base ==================================================================
    private void loadDoctors() {
        try {
            tableModel.setRowCount(0); // clear table first
            state = connect.createStatement();
            // NAME = last name, SURNAME = first name (same convention as patient table)
            ResultSet rs = state.executeQuery("SELECT NAME, SURNAME, SPECIALTY, PHONE FROM doctor");
            while (rs.next()) {
                String lastName   = rs.getString("NAME");
                String firstName  = rs.getString("SURNAME");
                String speciality = rs.getString("SPECIALTY");
                String phone      = rs.getString("PHONE");
                tableModel.addRow(new Object[]{lastName, firstName, speciality, phone});
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading doctors: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //add doctor to the data base
        if (e.getSource() == add_doctor) {
            try {
                state = connect.createStatement();

                String last  = lastNameField.getText().trim();
                String first = firstNameField.getText().trim();
                String spec  = (String) specialtyCombo.getSelectedItem();
                String ph    = phoneField.getText().trim();

                // NAME = last name, SURNAME = first name (matching loadDoctors)
                String sql = "INSERT INTO doctor (NAME,SURNAME,SPECIALTY,PHONE) VALUES ('"
                        + last + "','" + first + "','" + spec + "','" + ph + "')";
                state.execute(sql);
                state.execute("commit");
                loadDoctors();
                JOptionPane.showMessageDialog(add_doctor, "Doctor inserted!");
                clearFields();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        //delete doctor from the data base
        if (e.getSource() == delete_doctor) {
            int row = doctorTable.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(null, "Select a doctor!"); return; }
            answer = JOptionPane.showConfirmDialog(null, "Are you sure?", "Delete doctor", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                try {
                    //Delete from database
                    String lastName  = (String) tableModel.getValueAt(row, 0);
                    String firstName = (String) tableModel.getValueAt(row, 1);
                    String ph        = (String) tableModel.getValueAt(row, 3);

                    // NAME = last name, SURNAME = first name (matching loadDoctors)
                    String sql = "DELETE FROM doctor WHERE NAME='" + lastName
                            + "' AND SURNAME='" + firstName
                            + "' AND PHONE='" + ph + "'";
                    state = connect.createStatement();
                    state.execute(sql);
                    state.execute("commit");

                    //load doctors from the data base
                    loadDoctors();
                    clearFields();
                    JOptionPane.showMessageDialog(null, "Doctor deleted!");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }

        //edit doctor from data base
        if (e.getSource() == edit_doctor) {
            int row = doctorTable.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(null, "Select a doctor first!"); return; }

            try {
                state = connect.createStatement();
                String newLast  = lastNameField.getText().trim();
                String newFirst = firstNameField.getText().trim();
                String newSpec  = (String) specialtyCombo.getSelectedItem();
                String newPh    = phoneField.getText().trim();

                // NAME = last name, SURNAME = first name (matching loadDoctors)
                String sql = "UPDATE doctor SET "
                        + "NAME='"      + newLast  + "', "
                        + "SURNAME='"   + newFirst + "', "
                        + "SPECIALTY='" + newSpec  + "', "
                        + "PHONE='"     + newPh    + "' "
                        + "WHERE NAME='"    + originalLastName  + "' "
                        + "AND SURNAME='"   + originalFirstName + "'";

                state.execute(sql);
                state.execute("commit");

                //load doctors from the data base
                loadDoctors();
                JOptionPane.showMessageDialog(null, "Doctor updated!");
                clearFields();
            } catch (SQLException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error updating doctor: " + e1.getMessage());
            }
        }
    }

    private void clearFields() {
        lastNameField.setText("");
        firstNameField.setText("");
        phoneField.setText("");
        specialtyCombo.setSelectedIndex(0);
        doctorTable.clearSelection();
    }
}