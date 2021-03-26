import factory.Factory;
import factory.ProductWithQuantity;
import factory.ReceiptPrinter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import products.Product;
import utilities.Common;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestFileIO {
    private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private final String salesTaxRegex = "Sales Taxes: (?<totalSalesTax>[\\d\\.]+)";
    private final String totalCostRegex = "Total: (?<totalCost>[\\d\\.]+)";

    private List<String> outputList = new ArrayList<>();
    private double expectedTotalSalesTax = 0;
    private double expectedTotal = 0;
    private List<ProductWithQuantity> inventory;

    private List<String> fileOutputList = new ArrayList<>();

    @BeforeAll
    void setup(){
        System.setOut(new PrintStream(byteArrayOutputStream));

        /*
            2 imported bottles of perfume at 27.99
            1 bottle of perfume at 18.99
            1 packet of headache pills at 9.75
            1 box of imported chocolates at 11.25
        */

        Product importedBottlesOfPerfume = Factory.createProductAndApplyTaxes("imported bottles of perfume",27.99, true, true);
        Product bottleOfPerfume = Factory.createProductAndApplyTaxes("bottle of perfume",18.99, true, false);
        Product headachePills = Factory.createProductAndApplyTaxes("packet of headache pills", 9.75, false, false);
        Product boxOfImportedChocolates = Factory.createProductAndApplyTaxes("box of imported chocolates", 11.25, false, true);

        inventory = new ArrayList<>(Arrays.asList(
                new ProductWithQuantity(importedBottlesOfPerfume, 2),
                new ProductWithQuantity(bottleOfPerfume, 1),
                new ProductWithQuantity(headachePills, 1),
                new ProductWithQuantity(boxOfImportedChocolates, 1)
                ));

        ReceiptPrinter printer = new ReceiptPrinter(inventory);
        printer.generateReceipt();

        ExpectedValues expectedValues = TestCommon.generateExpectedValues(inventory);
        expectedTotal = expectedValues.getExpectedTotal();
        expectedTotalSalesTax = expectedValues.getExpectedTotalSalesTax();

        outputList = TestCommon.setOutputList(byteArrayOutputStream.toString());
        byteArrayOutputStream.reset();

        String outputFilePath = "./src/test/java/output.txt";
        Main.run("./input.txt", outputFilePath);
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(outputFilePath));
            String curLine;
            while((curLine = bufferedReader.readLine()) != null){
                fileOutputList.add(curLine.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testTotalCost(){
        TestCommon.testTotalCost(fileOutputList.get(outputList.size()-1), expectedTotal);
    }

    @Test
    void testTotalTax(){
        TestCommon.testTotalTax(fileOutputList.get(outputList.size()-2), expectedTotalSalesTax);
    }
}
