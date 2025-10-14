package daos.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnector implements Connector{
    private Connection conn;
    public Connection getConnection(){
        conn = null;

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/classicmodels";
        String username = "root";
        String password = "";

        try {
            // Load the database driver
            Class.forName(driver);
            // Get a connection to the database
            conn = DriverManager.getConnection(url, username, password);
        }catch(SQLException e){
            System.out.println("Connection could not be established - incorrect URL or database not switched on. ");
            System.out.println("Exception: " + e.getMessage());
        }catch(ClassNotFoundException e){
            System.out.println("Driver files have not been loaded. Please check pom driver dependencies details.");
            System.out.println("Exception: " + e.getMessage());
        }

        return conn;
    }

    public void freeConnection(){
        if(conn != null){
            try{
                conn.close();
                conn = null;
            }catch (SQLException e){
                System.out.println("An exception occurred when attempting to close the " +
                        "connection to the database");
            }
        }
    }
}
