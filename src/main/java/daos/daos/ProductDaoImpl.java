package daos.daos;



import daos.entities.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    private Connector connector;

    public ProductDaoImpl(Connector connector){
        this.connector = connector;
    }

    public List<Product> getAllProducts() throws SQLException{
        Connection conn = connector.getConnection();
        if(conn == null){
            throw new SQLException("getAllProducts(): Could not establish connection to database.");
        }

        ArrayList<Product> products = new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM products")) {
            try(ResultSet rs = ps.executeQuery()) {
                // Loop through the result set
                while(rs.next()){
                    Product product = mapProductRow(rs);
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

    private static Product mapProductRow(ResultSet rs) throws SQLException {
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
        return product;
    }


    public List<Product> getAllProductsContainingKeyword(String keyword) throws SQLException{
        Connection conn = connector.getConnection();
        if(conn == null){
            throw new SQLException("getAllProductsContainingKeyword(): Could not establish connection to database.");
        }

        ArrayList<Product> products = new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM products WHERE productDescription LIKE ?")) {
            ps.setString(1, "%" + keyword + "%");
            try(ResultSet rs = ps.executeQuery()) {
                // Loop through the result set
                while(rs.next()){
                    Product product = mapProductRow(rs);
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

    public Product getProductByCode(String prodCode) throws SQLException{
        Connection conn = connector.getConnection();
        if(conn == null){
            throw new SQLException("getProductByCode(): Could not establish connection to database.");
        }

        Product product = null;
        try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM products WHERE productCode = ?")) {
            ps.setString(1, prodCode);
            try(ResultSet rs = ps.executeQuery()) {
                // Loop through the result set
                if(rs.next()){
                    product = mapProductRow(rs);
                }
            }catch(SQLException e){
                System.out.println("An issue occurred when running the query or processing the resultset: " + e.getMessage());
            }
        }catch(SQLException e){
            System.out.println("The SQL query could not be prepared: " + e.getMessage());
        }
        return product;
    }

    public Product deleteProductByCode(String prodCode) throws SQLException{
        Product removed = getProductByCode(prodCode);
        if(removed == null){
            return removed;
        }

        Connection conn = connector.getConnection();
        if(conn == null){
            throw new SQLException("deleteProductByCode(): Could not establish connection to database.");
        }

        int deletedRows = 0;
        try(PreparedStatement ps = conn.prepareStatement("DELETE FROM products where productCode = ?")) {

           deletedRows = ps.executeUpdate();

        }catch(SQLException e){
            System.out.println("The SQL query could not be prepared: " + e.getMessage());
        }
        if(deletedRows == 0){
            removed = null;
        }

        return removed;
    }
}
