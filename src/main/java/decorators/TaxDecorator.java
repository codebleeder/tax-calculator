package decorators;

import products.Product;

public abstract class TaxDecorator extends Product {

    public TaxDecorator(double baseCost, String name) {
        super(baseCost, name);
    }
}
