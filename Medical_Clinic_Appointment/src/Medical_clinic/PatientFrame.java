package Medical_clinic;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dao.PatientDAO;
import model.Patient;

public class PatientFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	// colors
	Color tealGreen = new Color(0, 150, 136);
	Color white = new Color(255, 255, 255);
	Color mintColor = new Color(232, 245, 242);
	Color labelGray = new Color(100, 120, 115);
	Color tableHead = new Color(240, 244, 243);
	Color dangerRed = new Color(162, 45, 45);
	Color dangerBg = new Color(252, 235, 235);

	// buttons
	private JButton add_patient;
	private JButton delete_patient;
	private JButton edit_patient;

	// text fields
	private JTextField lastNameField;
	private JTextField firstNameField;
	private JTextField dateBirthField;
	private JTextField phoneField;
	private JTextField addressField;

	// table
	private JTable patientTable;
	private DefaultTableModel tableModel;

	public PatientFrame() {
		this.setTitle("Patients Management");
		this.setSize(1100, 560);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		JPanel panel = new JPanel(null);
		panel.setBackground(mintColor);
		this.setContentPane(panel);

		JLabel label = new JLabel("Patients Management");
		label.setFont(new Font("SansSerif", Font.BOLD, 28));
		label.setForeground(tealGreen);
		label.setBounds(20, 10, 500, 40);
		panel.add(label);

		// --- Left Panel: Patient Info ---
		JPanel info = new JPanel(null);
		info.setBackground(white);
		info.setBounds(15, 60, 450, 450);
		panel.add(info);

		JLabel cardTitle = new JLabel("PATIENT INFO");
		cardTitle.setFont(new Font("SansSerif", Font.BOLD, 11));
		cardTitle.setForeground(labelGray);
		cardTitle.setBounds(15, 12, 200, 16);
		info.add(cardTitle);

		// Fields
		addLabelAndField(info, "Last name", 38, lastNameField = new JTextField());
		addLabelAndField(info, "First name", 98, firstNameField = new JTextField());
		addLabelAndField(info, "Date of birth (DD/MM/YYYY)", 158, dateBirthField = new JTextField());
		addLabelAndField(info, "Phone №", 218, phoneField = new JTextField());
		addLabelAndField(info, "Address", 278, addressField = new JTextField());

		// Buttons
		add_patient = createButton("Add", 15, 360, tealGreen, white);
		info.add(add_patient);
		edit_patient = createButton("Edit", 220, 360, tealGreen, white);
		info.add(edit_patient);
		delete_patient = createButton("Delete", 15, 404, dangerBg, dangerRed);
		info.add(delete_patient);

		// --- Right Panel: Patient List ---
		JPanel tableCard = new JPanel(null);
		tableCard.setBackground(white);
		tableCard.setBounds(480, 60, 600, 450);
		panel.add(tableCard);

		String[] columns = { "Last name", "First name", "Date of birth", "Phone", "Address" };
		tableModel = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int col) { return false; }
		};

		patientTable = new JTable(tableModel);
		patientTable.setRowHeight(28);
		patientTable.setSelectionBackground(mintColor);
		patientTable.getTableHeader().setBackground(tableHead);

		JScrollPane scroll = new JScrollPane(patientTable);
		scroll.setBounds(15, 35, 568, 400);
		tableCard.add(scroll);

		// Selection Listener
		patientTable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				int row = patientTable.getSelectedRow();
				if (row >= 0) {
					lastNameField.setText((String) tableModel.getValueAt(row, 0));
					firstNameField.setText((String) tableModel.getValueAt(row, 1));
					dateBirthField.setText((String) tableModel.getValueAt(row, 2));
					phoneField.setText((String) tableModel.getValueAt(row, 3));
					addressField.setText((String) tableModel.getValueAt(row, 4));
				}
			}
		});

		this.setVisible(true);
	}

	// Helper for cleaner UI code
	private void addLabelAndField(JPanel panel, String text, int y, JTextField field) {
		JLabel lbl = new JLabel(text);
		lbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lbl.setForeground(labelGray);
		lbl.setBounds(15, y, 280, 16);
		panel.add(lbl);
		field.setBounds(15, y + 17, 410, 30);
		panel.add(field);
	}

	private JButton createButton(String text, int x, int y, Color bg, Color fg) {
		JButton btn = new JButton(text);
		btn.setBounds(x, y, 190, 34);
		btn.setBackground(bg);
		btn.setForeground(fg);
		btn.setFocusable(false);
		btn.addActionListener(this);
		return btn;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String last = lastNameField.getText().trim();
		String first = firstNameField.getText().trim();
		String dob = dateBirthField.getText().trim();
		String ph = phoneField.getText().trim();
		String adr = addressField.getText().trim();

		// ACTION: ADD
		if (e.getSource() == add_patient) {
		
			
			if (last.isEmpty() || first.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please fill names at least!");
				return;
			}

			Patient p = new Patient(last, first, dob, ph, adr);
			PatientDAO patientDao = new PatientDAO();
			
			if (patientDao.addPatient(p)) {
				tableModel.addRow(new Object[] { last, first, dob, ph, adr });
				clearFields();
				JOptionPane.showMessageDialog(null, "Saved to Database!");
			} else {
				JOptionPane.showMessageDialog(null, "Database Error!");
			}
		}

		// ACTION: DELETE
		else if (e.getSource() == delete_patient) {
			int row = patientTable.getSelectedRow();
			if (row < 0) {
				JOptionPane.showMessageDialog(null, "Select a patient first!");
				return;
			}
			int answer = JOptionPane.showConfirmDialog(null, "Are you sure?", "Delete", JOptionPane.YES_NO_OPTION);
			if (answer == JOptionPane.YES_OPTION) {
				tableModel.removeRow(row);
				clearFields();
			}
		}

		// ACTION: EDIT
		else if (e.getSource() == edit_patient) {
			int row = patientTable.getSelectedRow();
			if (row < 0) {
				JOptionPane.showMessageDialog(null, "Select a patient first!");
				return;
			}
			tableModel.setValueAt(last, row, 0);
			tableModel.setValueAt(first, row, 1);
			tableModel.setValueAt(dob, row, 2);
			tableModel.setValueAt(ph, row, 3);
			tableModel.setValueAt(adr, row, 4);
			JOptionPane.showMessageDialog(null, "Updated!");
		}
	}

	private void clearFields() {
		lastNameField.setText("");
		firstNameField.setText("");
		dateBirthField.setText("");
		phoneField.setText("");
		addressField.setText("");
		patientTable.clearSelection();
	}
}