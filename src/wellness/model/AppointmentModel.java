/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wellness.model;

/**
 *
 * @author Brett
 */
public class AppointmentModel {
    private int id;
    private String student;
    private String counselor;
    private String date;
    private String time;
    private String status;

    public AppointmentModel(int id, String student, String counselor, String date, String time, String status) {
        this.id = id;
        this.student = student;
        this.counselor = counselor;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public int getId() { return id; }
    public String getStudent() { return student; }
    public String getCounselor() { return counselor; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getStatus() { return status; }

    public void setId(int id) { this.id = id; }
    public void setStudent(String student) { this.student = student; }
    public void setCounselor(String counselor) { this.counselor = counselor; }
    public void setDate(String date) { this.date = date; }
    public void setTime(String time) { this.time = time; }
    public void setStatus(String status) { this.status = status; }
}
