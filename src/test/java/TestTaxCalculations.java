
import factory.Factory;
import models.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import products.Product;
import utilities.Common;

public class TestTaxCalculations {
    @Test
    void testSalesTax(){
        Product perfume = Factory.createBaseProduct("perfume", 27.99);

        perfume.addTax(Constants.SALES_TAX);
        double res = perfume.getCost();
        double expected = perfume.getBaseCost() + Common.roundOff(perfume.getBaseCost() * Constants.SALES_TAX);
        Assertions.assertEquals(expected, res);
    }

    @Test
    void testImportTax(){
        Product perfume = Factory.createBaseProduct("perfume", 27.99);
        perfume.addTax(Constants.IMPORT_TAX);
        double res = perfume.getCost();
        double expected = perfume.getBaseCost() + Common.roundOff(perfume.getBaseCost() * 0.05);
        Assertions.assertEquals(expected, res);
    }

    @Test
    void testSalesAndImportTax(){
        Product perfume = Factory.createBaseProduct("perfume", 27.99);
        perfume.addTax(Constants.SALES_TAX);
        perfume.addTax(Constants.IMPORT_TAX);
        double res = perfume.getCost();
        double expected = perfume.getBaseCost()
                + Common.roundOff(perfume.getBaseCost() * 0.10)
                + Common.roundOff(perfume.getBaseCost() * 0.05);
        Assertions.assertEquals(expected, res);
    }
}
