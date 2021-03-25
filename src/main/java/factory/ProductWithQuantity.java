package factory;

import products.Product;

public class ProductWithQuantity {
    private Product product;
    private long quantity;


    public ProductWithQuantity(Product product, long quantity) {
        this.quantity = quantity;
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public long getQuantity() {
        return quantity;
    }
}
