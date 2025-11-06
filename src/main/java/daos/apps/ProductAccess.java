package daos.apps;

import daos.daos.Connector;
import daos.daos.MySqlConnector;
import daos.daos.ProductDao;
import daos.daos.ProductDaoImpl;
import daos.entities.Product;
import lombok.extern.slf4j.Slf4j;
import services.ProductService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class ProductAccess {
    /**
     * Configure a ProductService object for use by this interface
     *
     * @param propertiesFilename The filename of the properties file containing database information
     * @return a fully configured ProductService containing a ProductDao
     */
    public static ProductService configureService(String propertiesFilename){
        Connector mySqlConnector = new MySqlConnector(propertiesFilename);
        ProductDao productDao = new ProductDaoImpl(mySqlConnector);
        ProductService productService = new ProductService(productDao);

        return productService;
    }
    public static void main(String[] args) {
        ProductService productService = configureService("properties/database.properties");
        Scanner scanner = new Scanner(System.in);
        try{
            System.out.print("Please enter keyword: ");
            String keyword = scanner.nextLine();

            try {
                List<Product> products = productService.getProductsByKeyword(keyword);
                System.out.println("-------------------------------");
                System.out.println("Products containing keyword: " + keyword);
                for (Product p : products) {
                    System.out.println(p.getProductCode() + ": " + p.getProductName());
                }
                System.out.println("-------------------------------");
            }catch(SQLException e) {
                System.out.println("A database error occurred. Cannot retrieve products for specified keyword: " + keyword);
                throw e;
            }

            String prodCode = "S10_1678";
            try {
                Product p = productService.getProductByCode(prodCode);
                if (p != null) {
                    System.out.println("Product matching " + prodCode + ": " + p.getProductName());
                } else {
                    System.out.println("No product found matching " + prodCode);
                }
            }catch(SQLException e) {
                System.out.println("Cannot carry out product retrieval. An error occurred when retrieving product " +
                        "matching product " +
                        "code: " + prodCode);
                throw e;
            }

            prodCode = "S10_1010";
            try {
                Product p = productService.getProductByCode(prodCode);
                if (p != null) {
                    System.out.println("Product matching " + prodCode + ": " + p.getProductName());
                } else {
                    System.out.println("No product found matching " + prodCode);
                }
            }catch(SQLException e) {
                System.out.println("Cannot carry out product retrieval. An error occurred when retrieving product " +
                        "matching product " +
                                "code: " + prodCode);
                throw e;
            }

            keyword = "the";
            List<Product> deleted = productService.deleteProductsByKeyword(keyword);
            System.out.println("Deleted products: " + deleted);
            //mySqlConnector.freeConnection();
        }catch(SQLException e){
            log.error("Database exception occurred. \nException: {}", e.getMessage());
        }
    }
}
