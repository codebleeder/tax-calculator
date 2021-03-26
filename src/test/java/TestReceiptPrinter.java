import decorators.ImportTaxDecorator;
import factory.Factory;
import factory.ProductWithQuantity;
import factory.ReceiptPrinter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import products.Product;
import utilities.Common;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestReceiptPrinter {
    private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private final String salesTaxRegex = "Sales Taxes: (?<totalSalesTax>[\\d\\.]+)";
    private final String totalCostRegex = "Total: (?<totalCost>[\\d\\.]+)";

    private List<String> outputList;
    private double expectedTotalSalesTax = 0;
    private double expectedTotal = 0;
    private List<ProductWithQuantity> inventory;

    @BeforeAll
    void setup(){
        System.setOut(new PrintStream(byteArrayOutputStream));

        Product book = Factory.createProductAndApplyTaxes("book", 12.49, false, false);
        Product musicCD = Factory.createProductAndApplyTaxes("Music CD", 14.99, true, false);
        Product chocolateBar = Factory.createProductAndApplyTaxes("Chocolate Bar", 0.85, false, false);
        Product perfume = Factory.createProductAndApplyTaxes("Perfume",47.50, true, true);

        inventory = new ArrayList<>(Arrays.asList(
                new ProductWithQuantity(book, 1),
                new ProductWithQuantity(musicCD, 1),
                new ProductWithQuantity(chocolateBar, 1),
                new ProductWithQuantity(perfume, 2)));

        ReceiptPrinter printer = new ReceiptPrinter(inventory);
        printer.generateReceipt();

        ExpectedValues expectedValues = TestCommon.generateExpectedValues(inventory);
        expectedTotalSalesTax = expectedValues.getExpectedTotalSalesTax();
        expectedTotal = expectedValues.getExpectedTotal();

        outputList = TestCommon.setOutputList(byteArrayOutputStream.toString());
    }

    @Test
    void testTotalSalesTax(){
        TestCommon.testTotalTax(outputList.get(outputList.size()-2), expectedTotalSalesTax);
    }

    @Test
    void testTotalCost(){
        TestCommon.testTotalCost(outputList.get(outputList.size()-1), expectedTotal);
    }

    @Test
    void testBookOutput(){
        testOutput(inventory.get(0), outputList.get(0));
    }

    @Test
    void testMusicCD_Output(){
        testOutput(inventory.get(1), outputList.get(1));
    }

    @Test
    void testChocolateBarOutput(){
        testOutput(inventory.get(2), outputList.get(2));
    }

    @Test
    void testPerfume2Output(){
        testOutput(inventory.get(3), outputList.get(3));
    }

    void testOutput(ProductWithQuantity productWithQuantity, String output){
        StringJoiner sj = new StringJoiner(" ");
        Product product = productWithQuantity.getProduct();
        long quantity = productWithQuantity.getQuantity();

        sj.add(String.valueOf(quantity))
                .add(product instanceof ImportTaxDecorator ? "imported" : "")
                .add(product.getName() + ":")
                .add(Common.numberFormat.format((quantity * product.getCost())));

        String expectedOutput = sj.toString();
        Assertions.assertEquals(expectedOutput, output);
    }
}
