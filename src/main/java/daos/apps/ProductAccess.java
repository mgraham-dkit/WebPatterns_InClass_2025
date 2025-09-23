package daos.apps;

import daos.daos.ProductDaoImpl;
import daos.entities.Product;

import java.sql.SQLException;
import java.util.List;

public class ProductAccess {
    public static void main(String[] args) {
        ProductDaoImpl productDao = new ProductDaoImpl();
        try {
            List<Product> products = productDao.getAllProductsContainingKeyword("wheel");
            for (Product p : products) {
                System.out.println(p.getProductCode() + ": " + p.getProductDescription());
            }
        }catch(SQLException e){
            System.out.println("Connection could not be established at this time. Try again later.");
        }
    }
}
