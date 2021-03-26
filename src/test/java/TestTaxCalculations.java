import decorators.ImportTaxDecorator;
import decorators.SalesTaxDecorator;
import factory.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import products.Product;
import utilities.Common;

public class TestTaxCalculations {
    @Test
    void testSalesTax(){
        Product perfume = Factory.createBaseProduct("perfume", 27.99);
        perfume = new SalesTaxDecorator(perfume);
        double res = perfume.getCost();
        double expected = perfume.getBaseCost() + Common.roundOff(perfume.getBaseCost() * 0.10);
        Assertions.assertEquals(expected, res);
    }

    @Test
    void testImportTax(){
        Product perfume = Factory.createBaseProduct("perfume", 27.99);
        perfume = new ImportTaxDecorator(perfume);
        double res = perfume.getCost();
        double expected = perfume.getBaseCost() + Common.roundOff(perfume.getBaseCost() * 0.05);
        Assertions.assertEquals(expected, res);
    }

    @Test
    void testSalesAndImportTax(){
        Product perfume = Factory.createBaseProduct("perfume", 27.99);
        perfume = new SalesTaxDecorator(perfume);
        perfume = new ImportTaxDecorator(perfume);
        double res = perfume.getCost();
        double expected = perfume.getBaseCost()
                + Common.roundOff(perfume.getBaseCost() * 0.10)
                + Common.roundOff(perfume.getBaseCost() * 0.05);
        Assertions.assertEquals(expected, res);
    }
}
