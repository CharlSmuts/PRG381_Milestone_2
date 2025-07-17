package wellness.controller;

import wellness.model.DBConnection;
import wellness.view.AppointmentView;

import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AppointmentController {
    private final AppointmentView view;
    private final DBConnection db;

    public AppointmentController(AppointmentView view) {
        this.view = view;
        this.db = new DBConnection();

        try {
            db.connect();
            if (!db.tableExists()) {
                db.createTable();
            }
            loadCounselors();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
        }

        addListeners();
        loadAppointments();
    }

    private void addListeners() {
        view.getBtnAdd().addActionListener(e -> addAppointment());
        view.getBtnUpdate().addActionListener(e -> updateAppointment());
        view.getBtnDelete().addActionListener(e -> deleteAppointment());

        view.getTableAppointments().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.getTableAppointments().getSelectedRow();
                view.getTxtStudent().setText(view.getTableAppointments().getValueAt(row, 1).toString());
                view.getCounselorCombo().setSelectedItem(view.getTableAppointments().getValueAt(row, 2).toString());
                view.getTxtDate().setText(view.getTableAppointments().getValueAt(row, 3).toString());
                view.getTxtTime().setText(view.getTableAppointments().getValueAt(row, 4).toString());
                view.getTxtStatus().setText(view.getTableAppointments().getValueAt(row, 5).toString());
            }
        });
    }

    private void loadAppointments() {
        try {
            ArrayList<String[]> appointments = db.viewAppointments();
            DefaultTableModel model = (DefaultTableModel) view.getTableAppointments().getModel();
            model.setRowCount(0);
            for (String[] row : appointments) {
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Load error: " + e.getMessage());
        }
    }

    public void loadCounselors() {
        try {
            ArrayList<String[]> counselors = db.viewCounselors();
            JComboBox<String> combo = view.getCounselorCombo();
            combo.removeAllItems();

            for (String[] row : counselors) {
                String name = row[1]; // row[1] = name
                combo.addItem(name);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to load counselors: " + e.getMessage());
        }
    }

    private boolean validateInput() {
        String student = view.getTxtStudent().getText().trim();
        String counselor = (String) view.getCounselorCombo().getSelectedItem();
        String dateStr = view.getTxtDate().getText().trim();
        String timeStr = view.getTxtTime().getText().trim();
        String status = view.getTxtStatus().getText().trim();

        if (student.isEmpty() || counselor == null || dateStr.isEmpty() || timeStr.isEmpty() || status.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields must be filled.");
            return false;
        }

        if (!student.matches("^[A-Za-z ]+$")) {
            JOptionPane.showMessageDialog(null, "Student name must contain only letters and spaces.");
            return false;
        }

        if (!counselor.matches("^[A-Za-z ]+$")) {
            JOptionPane.showMessageDialog(null, "Counselor name must contain only letters and spaces.");
            return false;
        }

        if (!status.matches("^[A-Za-z ]+$")) {
            JOptionPane.showMessageDialog(null, "Status must contain only letters and spaces.");
            return false;
        }

        try {
            Date.valueOf(dateStr); // yyyy-MM-dd
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Invalid date format. Use yyyy-MM-dd.");
            return false;
        }

        try {
            Time.valueOf(timeStr); // HH:mm:ss
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Invalid time format. Use HH:mm:ss.");
            return false;
        }

        String[] validStatuses = {"Scheduled", "Completed", "Cancelled"};
        boolean valid = false;
        for (String s : validStatuses) {
            if (status.equalsIgnoreCase(s)) {
                valid = true;
                break;
            }
        }
        if (!valid) {
            JOptionPane.showMessageDialog(null, "Status must be one of: Scheduled, Completed, or Cancelled.");
            return false;
        }

        return true;
    }

    private void addAppointment() {
        if (!validateInput()) return;

        try {
            String student = view.getTxtStudent().getText().trim();
            String counselor = (String) view.getCounselorCombo().getSelectedItem();
            Date date = Date.valueOf(view.getTxtDate().getText().trim());
            Time time = Time.valueOf(view.getTxtTime().getText().trim());
            String status = view.getTxtStatus().getText().trim();

            db.addDataAppointments(student, counselor, date, time, status);
            JOptionPane.showMessageDialog(null, "Appointment added.");
            loadAppointments();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Add error: " + e.getMessage());
        }
    }

    private void updateAppointment() {
        int row = view.getTableAppointments().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(null, "Please select an appointment to update.");
            return;
        }

        if (!validateInput()) return;

        try {
            int id = Integer.parseInt(view.getTableAppointments().getValueAt(row, 0).toString());
            String student = view.getTxtStudent().getText().trim();
            String counselor = (String) view.getCounselorCombo().getSelectedItem();
            Date date = Date.valueOf(view.getTxtDate().getText().trim());
            Time time = Time.valueOf(view.getTxtTime().getText().trim());
            String status = view.getTxtStatus().getText().trim();

            db.updateAppointment(student, counselor, date, time, status, id);
            JOptionPane.showMessageDialog(null, "Updated successfully.");
            loadAppointments();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Update error: " + e.getMessage());
        }
    }

    private void deleteAppointment() {
        int row = view.getTableAppointments().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(null, "Please select an appointment to delete.");
            return;
        }

        try {
            int id = Integer.parseInt(view.getTableAppointments().getValueAt(row, 0).toString());

            int confirm = JOptionPane.showConfirmDialog(null, "Delete this appointment?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            db.deleteAppointment(id);
            JOptionPane.showMessageDialog(null, "Deleted.");
            loadAppointments();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Delete error: " + e.getMessage());
        }
    }
}
