package daos.apps;

import daos.daos.ProductDao;
import daos.daos.ProductDaoImpl;
import daos.entities.Product;

import java.sql.SQLException;
import java.util.List;

public class ProductAccess {
    public static void main(String[] args) {
        ProductDao productDao = new ProductDaoImpl();
        try {
            List<Product> products = productDao.getAllProductsContainingKeyword("wheel");
            for (Product p : products) {
                System.out.println(p.getProductCode() + ": " + p.getProductDescription());
            }

            Product p = productDao.getProductByCode("S10_1678");
            if(p != null) {
                System.out.println("Product matching S10_1678: " + p.getProductName());
            }else{
                System.out.println("No product found matching S10_1678");
            }
        }catch(SQLException e){
            System.out.println("Connection could not be established at this time. Try again later.");
        }
    }
}
