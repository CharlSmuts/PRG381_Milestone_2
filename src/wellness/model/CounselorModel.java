/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wellness.model;

/**
 *
 * @author Michael
 */
public class CounselorModel {
    private String name;
    private String specialization;
    private boolean available;

    // Constructor for new counselors (no ID yet)
    public CounselorModel(String name, String specialization, boolean available) {
        this.name = name;
        this.specialization = specialization;
        this.available = available;
    } 

    // Getters
    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public boolean isAvailable() {
        return available;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    
}
