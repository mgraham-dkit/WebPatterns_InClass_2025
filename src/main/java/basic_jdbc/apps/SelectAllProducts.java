package basic_jdbc.apps;

import basic_jdbc.dtos.Product;

import java.sql.*;
import java.util.ArrayList;

public class SelectAllProducts {
    public static void main(String[] args) {
        ArrayList<Product> products = new ArrayList<>();

        // Create variables to hold database details
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/classicmodels";
        String username = "root";
        String password = "";

        try {
            // Load the database driver
            Class.forName(driver);
            // Get a connection to the database
            try(Connection conn = DriverManager.getConnection(url, username, password)) {

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
            }catch(SQLException e){
                System.out.println("Connection could not be established - incorrect URL or database not switched on: " + e.getMessage());
            }
        }catch(ClassNotFoundException e){
            System.out.println("Driver files have not been loaded. Please check pom  driver dependencies details: " + e.getMessage());
        }
        System.out.println("Products:");
        for(Product p: products){
            System.out.println(p.getProductCode() + ": " + p.getProductName());
        }
    }
}
