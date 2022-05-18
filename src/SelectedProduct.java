public class SelectedProduct {
    private String product_name;
    private double product_jumlah, product_total;

    public SelectedProduct(String product_name, double product_jumlah, double product_total) {
        this.product_name = product_name;
        this.product_jumlah = product_jumlah;
        this.product_total = product_total;
    }

    public String getProductName() {
        return this.product_name;
    }

    public double getProductJumlah() {
        return this.product_jumlah;
    }

    public double getProductTotal() {
        return this.product_total;
    }
}
