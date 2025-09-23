package daos.entities;

import java.util.Objects;

public class Product {
    /*
    CREATE TABLE products (
  productCode varchar(15) NOT NULL,
  productName varchar(70) NOT NULL,
  productLine varchar(50) NOT NULL,
  productScale varchar(10) NOT NULL,
  productVendor varchar(50) NOT NULL,
  productDescription text NOT NULL,
  quantityInStock smallint(6) NOT NULL,
  buyPrice double NOT NULL,
  MSRP double NOT NULL,
  PRIMARY KEY  (productCode),
  FOREIGN KEY (productLine) REFERENCES productlines(productLine)
);
     */

    private final String productCode;
    private final String productName;
    private final String productLine;
    private final String productScale;
    private final String productVendor;
    private final String productDescription;
    private final int quantityInStock;
    private final double buyPrice;
    private final double msrp;

    public Product(String productCode, String productName, String productLine, String productScale, String productVendor, String productDescription, int quantityInStock, double buyPrice, double msrp) {
        this.productCode = productCode;
        this.productName = productName;
        this.productLine = productLine;
        this.productScale = productScale;
        this.productVendor = productVendor;
        this.productDescription = productDescription;
        this.quantityInStock = quantityInStock;
        this.buyPrice = buyPrice;
        this.msrp = msrp;
    }

    public String getProductVendor() {
        return productVendor;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductLine() {
        return productLine;
    }

    public String getProductScale() {
        return productScale;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public double getMsrp() {
        return msrp;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;
        return Objects.equals(productCode, product.productCode);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productCode);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", productLine='" + productLine + '\'' +
                ", productScale='" + productScale + '\'' +
                ", productVendor='" + productVendor + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", quantityInStock=" + quantityInStock +
                ", buyPrice=" + buyPrice +
                ", msrp=" + msrp +
                '}';
    }
}
