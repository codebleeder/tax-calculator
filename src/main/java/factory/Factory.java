package factory;


import decorators.ImportTaxDecorator;
import decorators.SalesTaxDecorator;
import products.Product;

public class Factory {
    public static Product createProduct(Product product, boolean applySalesTax, boolean isImported){
        if (applySalesTax){
            product = new SalesTaxDecorator(product);
        }
        if (isImported) {
            product = new ImportTaxDecorator(product);
        }
        return product;
    }
}
