package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    // Utilise les mêmes infos que dans ton ConnectionBD
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "system"; 
    private static final String PASSWORD = "nour"; 

    public static Connection getConnection() {
        try {
            
            Class.forName("oracle.jdbc.OracleDriver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
            return null;
        }
    }
}