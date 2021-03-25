import decorators.ImportTaxDecorator;
import factory.Factory;
import factory.ProductWithQuantity;
import factory.ReceiptPrinter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import products.*;
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

        Product book = Factory.createProduct(new Book(), false, false);
        Product musicCD = Factory.createProduct(new MusicCD(), true, false);
        Product chocolateBar = Factory.createProduct(new ChocolateBar(), false, false);
        Product perfume2 = Factory.createProduct(new Perfume2(), true, true);

        inventory = new ArrayList<>(Arrays.asList(
                new ProductWithQuantity(book, 1),
                new ProductWithQuantity(musicCD, 1),
                new ProductWithQuantity(chocolateBar, 1),
                new ProductWithQuantity(perfume2, 2)));

        ReceiptPrinter printer = new ReceiptPrinter(inventory);
        printer.generateRecept();

        generateExpectedValues(inventory);
        setOutputList(byteArrayOutputStream.toString());
    }

    void generateExpectedValues(List<ProductWithQuantity> inventory){
        for(ProductWithQuantity productWithQuantity: inventory){
            double cost = productWithQuantity.getQuantity() * productWithQuantity.getProduct().getCost();
            double baseCost = productWithQuantity.getQuantity() * productWithQuantity.getProduct().getBaseCost();
            double tax = cost - baseCost;
            expectedTotalSalesTax += tax;
            expectedTotal += cost;
        }

    }

    void setOutputList(String multiLine){
        String[] outputs = multiLine.split("[\n\r]");
        outputList = Stream.of(outputs)
                .filter(s->!s.isBlank())
                .map(String::trim)
                .collect(Collectors.toList());
    }

    @Test
    void testTotalSalesTax(){
        Pattern pattern = Pattern.compile(salesTaxRegex);
        Matcher matcher = pattern.matcher(outputList.get(outputList.size()-2));
        if (matcher.find()){
            double totalSalesTax = Double.parseDouble(matcher.group("totalSalesTax"));
            Assertions.assertEquals(Common.numberFormat.format(expectedTotalSalesTax),
                    Common.numberFormat.format(totalSalesTax));
        }

    }

    @Test
    void testTotalCost(){
        Pattern pattern = Pattern.compile(totalCostRegex);
        Matcher matcher = pattern.matcher(outputList.get(outputList.size()-1));
        if (matcher.find()){
            double totalCost = Double.parseDouble(matcher.group("totalCost"));
            Assertions.assertEquals(Common.numberFormat.format(expectedTotal),
                    Common.numberFormat.format(totalCost));
        }
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
