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

public class AppointmentFrame extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    //colors 
    Color tealGreen = new Color(0, 150, 136);
    Color white     = new Color(255, 255, 255);
    Color mintColor = new Color(232, 245, 242);
    Color tableHead = new Color(240, 244, 243);
    Color dangerRed = new Color(162, 45, 45);
    Color dangerBg  = new Color(252, 235, 235);
    Color labelGray = new Color(100, 120, 115);

    private int answer;

    //buttons
    private JButton add_appointment;
    private JButton delete_appointment;
    private JButton edit_appointment;

    //text fields
    private JTextField patientNameField;
    private JTextField doctorNameField;
    private JTextField dateField;
    private JTextField timeField;
    private JComboBox<String> statusCombo;

    //table
    private JTable appointmentTable;
    private DefaultTableModel tableModel;

    private String[] statuses = {"Scheduled", "Confirmed", "Completed", "Cancelled"};

    //database connection
    private Connection connect;
    private Statement state;

    // save original values to use in edit WHERE clause
    private String originalPatient;
    private String originalDoctor;
    private String originalDate;
    private String originalTime;

    public AppointmentFrame() {
        //connect to the data base
        connect = DBConnection.getConnection();

        //================================================================
        this.setTitle("Appointments");
        this.setSize(1100, 560);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        JPanel panel = new JPanel(null);
        panel.setBackground(mintColor);
        this.setContentPane(panel);

        JLabel label = new JLabel("Appointments Management");
        label.setFont(new Font("SansSerif", Font.BOLD, 28));
        label.setForeground(tealGreen);
        label.setBounds(20, 10, 600, 40);
        panel.add(label);

        // left white card ==========================================================
        JPanel card = new JPanel(null);
        card.setBackground(white);
        card.setBounds(15, 60, 450, 450);
        panel.add(card);

        JLabel Title = new JLabel("APPOINTMENT INFO");
        Title.setFont(new Font("SansSerif", Font.BOLD, 11));
        Title.setForeground(labelGray);
        Title.setBounds(15, 12, 250, 16);
        card.add(Title);

        // patient name ====================================================
        JLabel patient_name = new JLabel("Patient full name");
        patient_name.setFont(new Font("SansSerif", Font.PLAIN, 13));
        patient_name.setForeground(labelGray);
        patient_name.setBounds(15, 38, 250, 16);
        card.add(patient_name);
        patientNameField = new JTextField();
        patientNameField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        patientNameField.setBounds(15, 55, 410, 30);
        card.add(patientNameField);

        // doctor name =========================================================
        JLabel doctor_name = new JLabel("Doctor full name");
        doctor_name.setFont(new Font("SansSerif", Font.PLAIN, 13));
        doctor_name.setForeground(labelGray);
        doctor_name.setBounds(15, 98, 250, 16);
        card.add(doctor_name);
        doctorNameField = new JTextField();
        doctorNameField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        doctorNameField.setBounds(15, 115, 410, 30);
        card.add(doctorNameField);

        // date ==================================================================
        JLabel date = new JLabel("Date (DD/MM/YYYY)");
        date.setFont(new Font("SansSerif", Font.PLAIN, 13));
        date.setForeground(labelGray);
        date.setBounds(15, 158, 250, 16);
        card.add(date);
        dateField = new JTextField();
        dateField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        dateField.setBounds(15, 175, 410, 30);
        card.add(dateField);

        // time ================================================================
        JLabel time = new JLabel("Time (HH:MM)");
        time.setFont(new Font("SansSerif", Font.PLAIN, 13));
        time.setForeground(labelGray);
        time.setBounds(15, 218, 250, 16);
        card.add(time);
        timeField = new JTextField();
        timeField.setFont(new Font("SansSerif", Font.PLAIN, 13));
        timeField.setBounds(15, 235, 410, 30);
        card.add(timeField);

        // status =============================================================
        JLabel status = new JLabel("Status");
        status.setFont(new Font("SansSerif", Font.PLAIN, 13));
        status.setForeground(labelGray);
        status.setBounds(15, 278, 200, 16);
        card.add(status);
        statusCombo = new JComboBox<>(statuses);
        statusCombo.setBounds(15, 295, 410, 30);
        statusCombo.setBackground(white);
        statusCombo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        statusCombo.setFocusable(false);
        card.add(statusCombo);

        // buttons =========================================================
        add_appointment = new JButton("Add");
        add_appointment.setBounds(15, 360, 190, 34);
        add_appointment.setBackground(tealGreen);
        add_appointment.setForeground(white);
        add_appointment.setFont(new Font("SansSerif", Font.BOLD, 14));
        add_appointment.setFocusable(false);
        add_appointment.addActionListener(this);
        card.add(add_appointment);

        edit_appointment = new JButton("Edit");
        edit_appointment.setBounds(220, 360, 190, 34);
        edit_appointment.setBackground(tealGreen);
        edit_appointment.setForeground(white);
        edit_appointment.setFont(new Font("SansSerif", Font.BOLD, 14));
        edit_appointment.setFocusable(false);
        edit_appointment.addActionListener(this);
        card.add(edit_appointment);

        delete_appointment = new JButton("Delete");
        delete_appointment.setBounds(15, 404, 190, 34);
        delete_appointment.setBackground(dangerBg);
        delete_appointment.setForeground(dangerRed);
        delete_appointment.setFont(new Font("SansSerif", Font.BOLD, 14));
        delete_appointment.setFocusable(false);
        delete_appointment.addActionListener(this);
        card.add(delete_appointment);

        //table of appointment =============================================================
        JPanel tableCard = new JPanel(null);
        tableCard.setBackground(white);
        tableCard.setBounds(480, 60, 600, 450);
        panel.add(tableCard);

        JLabel tableTitle = new JLabel("APPOINTMENT LIST");
        tableTitle.setFont(new Font("SansSerif", Font.BOLD, 11));
        tableTitle.setForeground(labelGray);
        tableTitle.setBounds(15, 12, 250, 16);
        tableCard.add(tableTitle);

        String[] columns = {"Patient", "Doctor", "Date", "Time", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };

        appointmentTable = new JTable(tableModel);
        appointmentTable.setRowHeight(28);
        appointmentTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        appointmentTable.setBackground(white);
        appointmentTable.setSelectionBackground(mintColor);
        appointmentTable.setSelectionForeground(new Color(0, 80, 70));
        appointmentTable.setShowVerticalLines(false);
        appointmentTable.setFocusable(false);
        appointmentTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        appointmentTable.getTableHeader().setBackground(tableHead);
        appointmentTable.getTableHeader().setForeground(labelGray);
        appointmentTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(appointmentTable);
        scroll.setBounds(15, 35, 568, 400);
        tableCard.add(scroll);

        appointmentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = appointmentTable.getSelectedRow();
                if (row >= 0) {
                    //Save original values to use in edit WHERE clause
                    originalPatient = (String) tableModel.getValueAt(row, 0);
                    originalDoctor  = (String) tableModel.getValueAt(row, 1);
                    originalDate    = (String) tableModel.getValueAt(row, 2);
                    originalTime    = (String) tableModel.getValueAt(row, 3);

                    patientNameField.setText(originalPatient);
                    doctorNameField.setText(originalDoctor);
                    dateField.setText(originalDate);
                    timeField.setText(originalTime);
                    statusCombo.setSelectedItem(tableModel.getValueAt(row, 4));
                }
            }
        });

        loadAppointments();
        this.setVisible(true);
    }

    // load appointments infos from the data base =================================================
    private void loadAppointments() {
        try {
            tableModel.setRowCount(0); // clear table first
            state = connect.createStatement();

            ResultSet rs = state.executeQuery(
                "SELECT PATIENT_ID, DOCTOR_ID, TO_CHAR(APP_DATE,'DD/MM/YYYY') AS APP_DATE, APP_TIME, STATUS_APP FROM appointment");

            while (rs.next()) {
                int    patientId = rs.getInt("PATIENT_ID");
                int    doctorId  = rs.getInt("DOCTOR_ID");
                String date      = rs.getString("APP_DATE");
                String time      = rs.getString("APP_TIME");
                String status    = rs.getString("STATUS_APP");

                // look up patient name using the patient ID
                ResultSet rsPat = connect.createStatement().executeQuery(
                    "SELECT NAME, SURNAME FROM patient WHERE PATIENT_ID = " + patientId);
                String patientName = "";
                if (rsPat.next()) {
                    patientName = rsPat.getString("NAME") + " " + rsPat.getString("SURNAME");
                }
                rsPat.close();

                // look up doctor name using the doctor ID
                ResultSet rsDoc = connect.createStatement().executeQuery(
                    "SELECT NAME, SURNAME FROM doctor WHERE DOCTOR_ID = " + doctorId);
                String doctorName = "";
                if (rsDoc.next()) {
                    doctorName = rsDoc.getString("NAME") + " " + rsDoc.getString("SURNAME");
                }
                rsDoc.close();

                tableModel.addRow(new Object[]{patientName, doctorName, date, time, status});
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading appointments: " + e.getMessage());
        }
    }
    //========================================================================================================
    
    @Override
    public void actionPerformed(ActionEvent e) {

        //add appointment to the data base
        if (e.getSource() == add_appointment) {
            String patientName = patientNameField.getText().trim();
            String doctorName  = doctorNameField.getText().trim();
            String d           = dateField.getText().trim();
            String t           = timeField.getText().trim();
            String status      = (String) statusCombo.getSelectedItem();

            if (patientName.isEmpty() || doctorName.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all fields!");
                return;
            }

            try {
                // split patient full name into last name and first name
                String patLast  = patientName.split(" ")[0];
                String patFirst = patientName.split(" ")[1];

                // look up patient ID using last name and first name separately
                state = connect.createStatement();
                ResultSet rsPat = state.executeQuery(
                    "SELECT PATIENT_ID FROM patient WHERE NAME = '" + patLast + "' AND SURNAME = '" + patFirst + "'");
                if (!rsPat.next()) {
                    JOptionPane.showMessageDialog(null, "Patient not found: " + patientName);
                    return;
                }
                int patientId = rsPat.getInt("PATIENT_ID");
                rsPat.close();

                // split doctor full name into last name and first name
                String docLast  = doctorName.split(" ")[0];
                String docFirst = doctorName.split(" ")[1];

                // look up doctor ID using last name and first name separately
                state = connect.createStatement();
                ResultSet rsDoc = state.executeQuery(
                    "SELECT DOCTOR_ID FROM doctor WHERE NAME = '" + docLast + "' AND SURNAME = '" + docFirst + "'");
                if (!rsDoc.next()) {
                    JOptionPane.showMessageDialog(null, "Doctor not found: " + doctorName);
                    return;
                }
                int doctorId = rsDoc.getInt("DOCTOR_ID");
                rsDoc.close();

                // insert the appointment using the IDs we found
                state = connect.createStatement();
                String sql = "INSERT INTO appointment (APP_DATE, APP_TIME, STATUS_APP, PATIENT_ID, DOCTOR_ID) VALUES ("
                        + "TO_DATE('" + d + "','DD/MM/YYYY'),'" + t + "','" + status + "'," + patientId + "," + doctorId + ")";
                state.execute(sql);
                state.execute("commit");

                loadAppointments();
                JOptionPane.showMessageDialog(add_appointment, "Appointment inserted!");
                clearFields();
            } catch (SQLException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error adding appointment: " + e1.getMessage());
            }
        }

        //delete appointment from the data base
        if (e.getSource() == delete_appointment) {
            int row = appointmentTable.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(null, "Select an appointment first!"); return; }
            answer = JOptionPane.showConfirmDialog(null, "Are you sure?", "Delete appointment", JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                try {
                    String patientName = (String) tableModel.getValueAt(row, 0);
                    String doctorName  = (String) tableModel.getValueAt(row, 1);
                    String date        = (String) tableModel.getValueAt(row, 2);
                    String time        = (String) tableModel.getValueAt(row, 3);

                    // split full names back into last name and first name
                    String patLast  = patientName.split(" ")[0];
                    String patFirst = patientName.split(" ")[1];
                    String docLast  = doctorName.split(" ")[0];
                    String docFirst = doctorName.split(" ")[1];

                    // delete using last name and first name separately instead of ||
                    String sql = "DELETE FROM appointment WHERE "
                            + "APP_DATE = TO_DATE('" + date + "','DD/MM/YYYY') "
                            + "AND APP_TIME = '" + time + "' "
                            + "AND PATIENT_ID = (SELECT PATIENT_ID FROM patient WHERE NAME = '" + patLast  + "' AND SURNAME = '" + patFirst + "') "
                            + "AND DOCTOR_ID  = (SELECT DOCTOR_ID  FROM doctor  WHERE NAME = '" + docLast  + "' AND SURNAME = '" + docFirst + "')";
                    state = connect.createStatement();
                    state.execute(sql);
                    state.execute("commit");

                    //load appointments from the data base
                    loadAppointments();
                    clearFields();
                    JOptionPane.showMessageDialog(null, "Appointment deleted!");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error deleting appointment: " + e1.getMessage());
                }
            }
        }

        //edit appointment from data base
        if (e.getSource() == edit_appointment) {
            int row = appointmentTable.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(null, "Select an appointment first!"); return; }

            try {
                state = connect.createStatement();
                String newPatient = patientNameField.getText().trim();
                String newDoctor  = doctorNameField.getText().trim();
                String newDate    = dateField.getText().trim();
                String newTime    = timeField.getText().trim();
                String newStatus  = (String) statusCombo.getSelectedItem();

                // split new full names into last name and first name
                String newPatLast  = newPatient.split(" ")[0];
                String newPatFirst = newPatient.split(" ")[1];
                String newDocLast  = newDoctor.split(" ")[0];
                String newDocFirst = newDoctor.split(" ")[1];

                // split original full names into last name and first name
                String oriPatLast  = originalPatient.split(" ")[0];
                String oriPatFirst = originalPatient.split(" ")[1];
                String oriDocLast  = originalDoctor.split(" ")[0];
                String oriDocFirst = originalDoctor.split(" ")[1];

                // update using last name and first name separately instead of ||
                String sql = "UPDATE appointment SET "
                        + "APP_DATE   = TO_DATE('" + newDate   + "','DD/MM/YYYY'), "
                        + "APP_TIME   = '" + newTime   + "', "
                        + "STATUS_APP = '" + newStatus + "', "
                        + "PATIENT_ID = (SELECT PATIENT_ID FROM patient WHERE NAME = '" + newPatLast  + "' AND SURNAME = '" + newPatFirst + "'), "
                        + "DOCTOR_ID  = (SELECT DOCTOR_ID  FROM doctor  WHERE NAME = '" + newDocLast  + "' AND SURNAME = '" + newDocFirst + "') "
                        + "WHERE APP_DATE = TO_DATE('" + originalDate + "','DD/MM/YYYY') "
                        + "AND   APP_TIME = '" + originalTime + "' "
                        + "AND   PATIENT_ID = (SELECT PATIENT_ID FROM patient WHERE NAME = '" + oriPatLast  + "' AND SURNAME = '" + oriPatFirst + "') "
                        + "AND   DOCTOR_ID  = (SELECT DOCTOR_ID  FROM doctor  WHERE NAME = '" + oriDocLast  + "' AND SURNAME = '" + oriDocFirst + "')";

                state.execute(sql);
                state.execute("commit");

                //load appointments from the data base
                loadAppointments();
                JOptionPane.showMessageDialog(null, "Appointment updated!");
                clearFields();
            } catch (SQLException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error updating appointment: " + e1.getMessage());
            }
        }
    }
    
    //======================================================================================================
    private void clearFields() {
        patientNameField.setText("");
        doctorNameField.setText("");
        dateField.setText("");
        timeField.setText("");
        statusCombo.setSelectedIndex(0);
        appointmentTable.clearSelection();
    }
}