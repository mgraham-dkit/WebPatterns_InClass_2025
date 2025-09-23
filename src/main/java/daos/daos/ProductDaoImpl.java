package daos.daos;



import daos.entities.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl {
    public Connection getConnection(){
        Connection conn = null;

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

    public List<Product> getAllProducts() throws SQLException{
        Connection conn = getConnection();
        if(conn == null){
            throw new SQLException("getAllProducts(): Could not establish connection to database.");
        }

        ArrayList<Product> products = new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM products")) {
            try(ResultSet rs = ps.executeQuery()) {
                // Loop through the result set
                while(rs.next()){
                    Product product = new Product(
                            rs.getString("productCode"),
                            rs.getString("productName"),
                            rs.getString("productLine"),
                            rs.getString("productScale"),
                            rs.getString("productVendor"),
                            rs.getString("productDescription"),
                            rs.getInt("quantityInStock"),
                            rs.getDouble("buyPrice"),
                            rs.getDouble("MSRP")
                    );
                    products.add(product);
                }
            }catch(SQLException e){
                System.out.println("An issue occurred when running the query or processing the resultset: " + e.getMessage());
            }
        }catch(SQLException e){
            System.out.println("The SQL query could not be prepared: " + e.getMessage());
        }
        return products;
    }


    public List<Product> getAllProductsContainingKeyword(String keyword) throws SQLException{
        Connection conn = getConnection();
        if(conn == null){
            throw new SQLException("getAllProductsContainingKeyword(): Could not establish connection to database.");
        }

        ArrayList<Product> products = new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM products WHERE productDescription LIKE ?")) {
            ps.setString(1, "%" + keyword + "%");
            try(ResultSet rs = ps.executeQuery()) {
                // Loop through the result set
                while(rs.next()){
                    Product product = new Product(
                            rs.getString("productCode"),
                            rs.getString("productName"),
                            rs.getString("productLine"),
                            rs.getString("productScale"),
                            rs.getString("productVendor"),
                            rs.getString("productDescription"),
                            rs.getInt("quantityInStock"),
                            rs.getDouble("buyPrice"),
                            rs.getDouble("MSRP")
                    );
                    products.add(product);
                }
            }catch(SQLException e){
                System.out.println("An issue occurred when running the query or processing the resultset: " + e.getMessage());
            }
        }catch(SQLException e){
            System.out.println("The SQL query could not be prepared: " + e.getMessage());
        }
        return products;
    }
}
