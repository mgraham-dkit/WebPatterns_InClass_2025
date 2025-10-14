package daos.daos;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
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
            log.error("Connection could not be established - incorrect URL or database not switched on. \n Exception:" +
                    " {}", e.getMessage());
        }catch(ClassNotFoundException e){
            log.error("Driver files have not been loaded. Please check pom driver dependencies details. \n Exception:" +
                    " {}", e.getMessage());
        }

        return conn;
    }

    public void freeConnection(){
        if(conn != null){
            try{
                conn.close();
                conn = null;
            }catch (SQLException e){
                log.error("An exception occurred when attempting to close the connection to the database \n " +
                        "Exception:" +
                        " {}", e.getMessage());
            }
        }
    }
}
