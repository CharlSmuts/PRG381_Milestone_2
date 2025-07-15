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

                    String Query2 = "CREATE TABLE counselors(cid INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), name VARCHAR(100), "+
                            "specialization VARCHAR(100), available BOOLEAN)";
                    this.con.createStatement().execute(Query2);

                    String Query3 = "CREATE TABLE feedback(fid INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), student VARCHAR(100), comments varchar(100))";
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
    
    public void addData(String table){
        
    }
}
