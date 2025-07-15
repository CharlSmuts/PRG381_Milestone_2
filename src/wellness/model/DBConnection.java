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
                    String Query1 = "CREATE TABLE appointments(student varchar(20), counselor varchar(20), date date, time time , status varchar(10))";
                    this.con.createStatement().execute(Query1);

                    String Query2 = "CREATE TABLE counselors(name varchar(20), specialization varchar(20), available boolean)";
                    this.con.createStatement().execute(Query2);

                    String Query3 = "CREATE TABLE feedback(student varchar(20), comments varchar(100))";
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
