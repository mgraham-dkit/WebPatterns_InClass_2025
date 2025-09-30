package daos.daos;

import daos.entities.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {
    public List<Product> getAllProducts() throws SQLException;
    public List<Product> getAllProductsContainingKeyword(String keyword) throws SQLException;
    public Product getProductByCode(String prodCode) throws SQLException;
    public Product deleteProductByCode(String prodCode) throws SQLException;
}
