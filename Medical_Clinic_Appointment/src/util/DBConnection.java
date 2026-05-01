package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL      = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String USER     = "alaouchiche";
    private static final String PASSWORD = "naziha";

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