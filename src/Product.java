public class Product {
    private String product_name;
    private double product_price;

    public Product(String product_name, double product_price) {
        this.product_name = product_name;
        this.product_price = product_price;
    }

    public String getProductName() {
        return this.product_name;
    }

    public double getProductPrice() {
        return this.product_price;
    }

    public void setProductName(String new_name) {
        this.product_name = new_name;
    }

    public void setProductPrice(double new_price) {
        this.product_price = new_price;
    }
}
