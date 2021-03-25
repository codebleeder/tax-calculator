package decorators;

import products.Product;
import utilities.Common;

public class ImportTaxDecorator extends TaxDecorator {
    private Product product;
    private static final double tax = 0.05;

    public ImportTaxDecorator(Product product) {
        super(product.getBaseCost(), product.getName());
        this.product = product;
        // setBaseCost(product.getBaseCost());
        // setName(product.getName());

    }

    public double getCost() {
        return product.getCost() + Common.roundOff(product.getBaseCost() * tax);
    }


}
