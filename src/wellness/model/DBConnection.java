/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wellness.model;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.*;
import java.sql.Time;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Administrator
 */
public class DBConnection {
    
    public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String JDBC_URL = "jdbc:derby:WellnessDB;create=true";
    
    Connection con;
    
    public DBConnection(){
    }
    
    public void connect() throws ClassNotFoundException
    {
        try 
        {
            Class.forName(DRIVER); //loads jdbc driver class at runtime
            this.con = DriverManager.getConnection(JDBC_URL);
            if(this.con != null)
            {
                System.out.println("Connected to DB");
            }
            else
            {
                System.out.println("DB Connection failed");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    public void createTable()
    {       
        System.out.println("creating tables");
            try 
                {
                    String Query1 = "CREATE TABLE appointments(aid INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), student VARCHAR(100) NOT NULL, "
                            +  "counselor VARCHAR(100) NOT NULL, appointment_date DATE NOT NULL, appointment_time TIME NOT NULL,  status VARCHAR(50) NOT NULL)";
                    
                    this.con.createStatement().execute(Query1);

                    String Query2 = "CREATE TABLE counselors(cid INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), name VARCHAR(100) NOT NULL, "+
                            "specialization VARCHAR(100) NOT NULL, available BOOLEAN NOT NULL)";
                    this.con.createStatement().execute(Query2);

                    String Query3 = "CREATE TABLE feedback(fid INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), student VARCHAR(100) NOT NULL, comments VARCHAR(100) NOT NULL, "+
                            "feedback INTEGER NOT NULL)";
                    this.con.createStatement().execute(Query3);                  
                }
        catch (SQLException e)
                {
                    e.printStackTrace();
                }
    }
    
    public boolean tableExists() throws SQLException
    {
        boolean exists = false;
        DatabaseMetaData dbm = con.getMetaData(); // get database info from connection
        try
        {
            boolean deletetables = false; // set to true to recreate tables
            
            if (deletetables)
            {
               String Q1 = "DROP TABLE appointments";
                String Q2 = "DROP TABLE counselors";
                String Q3 = "DROP TABLE feedback";   
                this.con.createStatement().execute(Q1);
                this.con.createStatement().execute(Q2);
                this.con.createStatement().execute(Q3);
                System.out.println("Tables deleted"); 
            }
            
            
            ResultSet rs = dbm.getTables(null, null, "APPOINTMENTS", null); //gets the appointments table from the database
            exists = rs.next(); // checks if the table exists and if it does sets exists to true. We only check 1 table because if 1 exists all of them will exist
        }
        catch ( SQLException e)
        {
            e.printStackTrace();    
        }
        //System.out.println(exists);
       return exists; 
    }
    /*
    //
    //DATA ADDING UNTESTED
    //
    */
    //add
    public void addDataAppointments(String student, String counselor, Date appointment_date,  Time appointment_time, String status)
    {
        String Q = "INSERT INTO appointments (student, counselor, appointment_date, appointment_time, status) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = con.prepareStatement(Q);
            
            statement.setString(1, student);
            statement.setString(2, counselor);
            statement.setDate(3, appointment_date);
            statement.setTime(4, appointment_time);
            statement.setString(5, status);
            statement.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void addDataCounselors(String name, String spec, boolean available)
    {
       String Q = "INSERT INTO counselors (name, specialization, available) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = con.prepareStatement(Q);
            
            statement.setString(1, name);
            statement.setString(2, spec);
            statement.setBoolean(3, available);
            statement.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
        
    public void addDataFeedback(String student, String comment, Integer feedback)
    {
        String Q = "INSERT INTO feedback (student, comments, feedback) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = con.prepareStatement(Q);
            
            statement.setString(1, student);
            statement.setString(2, comment);
            statement.setInt(3, feedback);
            statement.executeUpdate();
            System.out.println("added to feedback" + student + ", " + comment + ", " + feedback);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    //View
    public ArrayList<String[]> viewFeedback()
    {
        ArrayList<String[]> datalist = new ArrayList<>();
        
        
        try {
            String Q = "SELECT * FROM feedback";
            ResultSet table = this.con.createStatement().executeQuery(Q);
            
            while (table.next()) {
                int fid = table.getInt("fid");
                String student = table.getString("student");
                String comment = table.getString("comments");
                int feedback = table.getInt("feedback");
                
                String[] row = {Integer.toString(fid), student, comment, Integer.toString(feedback)};
                datalist.add(row);               
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return datalist;
    }
    
    public void viewAppointments()
    {
        
    }
    
    public void viewCounselors()
    {
        
    }
    //update
    public void updateAppointment(String student, String counselor, Date appointment_date,  Time appointment_time, String status, Integer aid)
    {
        String Q = "UPDATE appointments SET student = ?, counselor = ?, appointment_date = ?, appointment_time = ?, status = ? WHERE aid = ?";
        try {
            PreparedStatement statement = con.prepareStatement(Q);
            
            statement.setString(1, student);
            statement.setString(2, counselor);
            statement.setDate(3, appointment_date);
            statement.setTime(4, appointment_time);
            statement.setString(5, status);
            statement.setInt(6, aid);
            statement.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }
    
    public void updateCounselors(String name, String spec, boolean available, Integer cid)
    {
        String Q = "UPDATE counselors SET name = ?, specialization = ?, available = ? WHERE cid = ?";
        try {
            PreparedStatement statement = con.prepareStatement(Q);
            
            statement.setString(1, name);
            statement.setString(2, spec);
            statement.setBoolean(3, available);
            statement.setInt(4, cid);
            statement.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }   
    }
    
    public void updateFeedback(String student, String comment, Integer feedback, Integer fid)
    {
         String Q = "UPDATE feedback SET student = ?, comments = ?, feedback = ? WHERE fid = ?";
        try {
            PreparedStatement statement = con.prepareStatement(Q);
            
            statement.setString(1, student);
            statement.setString(2, comment);
            statement.setInt(3, feedback);
            statement.setInt(4, fid);
            statement.executeUpdate();
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    //delete
    public void deleteAppointment(Integer aid)
    {
         String Q = "DELETE FROM appointments WHERE aid = ?";
        try {
            PreparedStatement statement = con.prepareStatement(Q);
            
            statement.setInt(1, aid);
            statement.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void deleteCounselor(Integer cid)
    {
        String Q = "DELETE FROM counselors WHERE cid = ?";
        try {
            PreparedStatement statement = con.prepareStatement(Q);
            
            statement.setInt(1, cid);

            statement.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void deleteFeedback(Integer fid)
    {
        String Q = "DELETE FROM feedback WHERE fid = ?";
        try {
            PreparedStatement statement = con.prepareStatement(Q);
            
            statement.setInt(1, fid);

            statement.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
