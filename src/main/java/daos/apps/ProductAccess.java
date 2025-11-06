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
    private static ProductService productService;
    private static Scanner input = new Scanner(System.in);

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
        productService = configureService("properties/database.properties");

        boolean keepRunning = true;

        while(keepRunning) {
            System.out.println("Please choose from the following options: ");
            displayMenu();
            String choice = input.nextLine();
            try{
                switch(choice) {
                    case "-1" -> {
                        System.out.println("Now terminating...");
                        keepRunning = false;
                    }
                    case "1" -> getProductsByKeyword();
                    case "2" -> getProductByCode();
                    case "3" -> deleteProductsByKeyword();
                    default -> System.out.println("Not a valid choice, please try again.");
                }
            } catch (SQLException e) {
                log.error("Database exception occurred. \nException: {}", e.getMessage());
            }
        }
        // Shut down connection in use
        productService.shutdownService();
        System.out.println("Connection terminated. Goodbye.");
    }

    private static void displayMenu(){
        System.out.println("1) Display all products matching specific keyword");
        System.out.println("2) Display the product matching specific product code");
        System.out.println("3) Delete all products matching specific keyword");
        System.out.println("-1) Exit program");
    }

    private static void deleteProductsByKeyword() throws SQLException {
        System.out.print("Please enter keyword to delete products for: ");
        String keyword = input.nextLine();
        System.out.println();

        try {
            List<Product> deleted = productService.deleteProductsByKeyword(keyword);
            if(deleted.isEmpty()){
                System.out.println("No products were deleted for keyword \"" + keyword + "\".");
            }else{
                System.out.println("Deleted products: " + deleted);
            }

        }catch(SQLException e){
            System.out.println("A database error occurred. Could not delete products containing specified keyword: " + keyword);
            throw e;
        }
    }

    private static void getProductByCode() throws SQLException {
        System.out.print("Please enter product code: ");
        String prodCode = input.nextLine();
        System.out.println();

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
    }

    private static void getProductsByKeyword() throws SQLException {
        System.out.print("Please enter keyword: ");
        String keyword = input.nextLine();

        try {
            List<Product> products = productService.getProductsByKeyword(keyword);
            System.out.println("-------------------------------");
            if(products.isEmpty()){
                System.out.println("No products found for keyword \"" + keyword + "\".");
            }else {
                System.out.println("Products containing keyword: " + keyword);
                for (Product p : products) {
                    System.out.println(p.getProductCode() + ": " + p.getProductName());
                }
            }
            System.out.println("-------------------------------");
        }catch(SQLException e) {
            System.out.println("A database error occurred. Cannot retrieve products for specified keyword: " + keyword);
            throw e;
        }
    }
}
