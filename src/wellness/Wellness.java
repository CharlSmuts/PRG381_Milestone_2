/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package wellness;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import wellness.model.DBConnection;
import wellness.view.Dashboard;
/**
 *
 * @author Koekie
 */
public class Wellness {
    public static DBConnection db = new DBConnection();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable(){
            public void run()
            {
                try 
                {
                    db.connect();
                    try {
                        if (db.tableExists()) {
                            System.out.println("Tables already exist, cont.");
                        }
                        else
                        {
                            db.createTable();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                catch (ClassNotFoundException ex)
                {
                    ex.printStackTrace();
                } 
                new Dashboard().setVisible(true);
               
            }
        });
    }
    
}
