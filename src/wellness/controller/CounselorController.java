/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wellness.controller;

import wellness.model.CounselorModel;
import wellness.view.CounselorPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import wellness.model.DBConnection;
import java.util.*;

/**
 *
 * @author Michael
 */
public class CounselorController {
    
        private final CounselorPanel view;
    private final DBConnection db;

    public CounselorController(CounselorPanel view, DBConnection db) {
        this.view = view;
        this.db = db;

        view.getAddButton().addActionListener(new AddButtonListener());
        view.getUpdateButton().addActionListener(new UpdateButtonListener());
        view.getDeleteButton().addActionListener(new DeleteButtonListener());
        view.getClearButton().addActionListener(new ClearButtonListener());

        populateTable();
    }

    // Populate the JTable from DB
    public void populateTable() {
        view.getTableModel().setRowCount(0); // clear existing data

        ArrayList<String[]> counselorList = db.viewCounselors();  
        for (String[] row : counselorList) {
            String id = row[0];
            String name = row[1];
            String specialization = row[2];
            String availability = row[3];

            view.getTableModel().addRow(new Object[]{id, name, specialization, availability});
        }
    }

    //  Input Validator
    private boolean validateInputs(String name, String specialization) {
        if (name.isEmpty()) {
            view.throwWarning("Name field may not be empty.", "Validation Error");
            return false;
        } else if (name.length() > 50) {
            view.throwWarning("Name may not exceed 50 characters.", "Validation Error");
            return false;
        }

        if (specialization.isEmpty()) {
            view.throwWarning("Specialization field may not be empty.", "Validation Error");
            return false;
        } else if (specialization.length() > 100) {
            view.throwWarning("Specialization may not exceed 100 characters.", "Validation Error");
            return false;
        }

        return true;
    }

    //  Add Counselor
    class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = view.getNameInput();
            String specialization = view.getSpecializationInput();
            boolean available = view.getAvailabilityInput();

            if (!validateInputs(name, specialization)) return;

            view.clearFields();

            
            if (db.addDataCounselors(name, specialization, available)) {
                view.throwSuccess("Counselor added successfully.", "Add Success");
                populateTable();
            } else {
                view.throwWarning("Failed to add counselor.", "Database Error");
            }
        }
    }

    // ️ Update Counselor
    class UpdateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedId = view.getSelectedID();
            if (Objects.equals(selectedId, "")) {
                view.throwWarning("You must select a valid row to update.", "Update Error");
                return;
            }

            String name = view.getNameInput();
            String specialization = view.getSpecializationInput();
            boolean available = view.getAvailabilityInput();

            if (!validateInputs(name, specialization)) return;

            view.clearFields();

            
            
            if (db.updateCounselors(name, specialization, available, Integer.valueOf(selectedId))) {
                view.throwSuccess("Counselor updated successfully.", "Update Success");
                populateTable();
            } else {
                view.throwWarning("Failed to update counselor.", "Database Error");
            }
        }
    }

    //  Delete Counselor
    class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedId = view.getSelectedID();
            if (Objects.equals(selectedId, "")) {
                view.throwWarning("You must select a valid row to delete.", "Delete Error");
                return;
            }

            view.clearFields();

            try {
                if (db.deleteCounselor(Integer.valueOf(selectedId))) {
                    view.throwSuccess("Counselor deleted successfully.", "Delete Success");
                    populateTable();
                } else {
                    view.throwWarning("Failed to delete counselor.", "Database Error");
                }
            } catch (Exception ex) {
                view.throwWarning("Error: " + ex.getMessage(), "Exception");
            }
        }
    }
    
    class ClearButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        view.clearFields();
    }
}
}
    
