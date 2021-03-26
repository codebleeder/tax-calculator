import factory.ProductWithQuantity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utilities.Common;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestCommon {
    private static final String salesTaxRegex = "Sales Taxes: (?<totalSalesTax>[\\d\\.]+)";
    private static final String totalCostRegex = "Total: (?<totalCost>[\\d\\.]+)";

    public static ExpectedValues generateExpectedValues(List<ProductWithQuantity> inventory){
        double expectedTotalSalesTax = 0;
        double expectedTotal = 0;

        for(ProductWithQuantity productWithQuantity: inventory){
            double cost = productWithQuantity.getQuantity() * productWithQuantity.getProduct().getCost();
            double baseCost = productWithQuantity.getQuantity() * productWithQuantity.getProduct().getBaseCost();
            double tax = cost - baseCost;
            expectedTotalSalesTax += tax;
            expectedTotal += cost;
        }
        return new ExpectedValues(expectedTotal, expectedTotalSalesTax);
    }

    public static List<String> setOutputList(String multiLine){
        String[] outputs = multiLine.split("[\n\r]");
        List<String> list = new ArrayList<>();
        list = Stream.of(outputs)
                .filter(s->!s.isBlank())
                .map(String::trim)
                .collect(Collectors.toList());
        return list;
    }

    public static void testTotalCost(String outputLine, double expectedTotal){
        Pattern pattern = Pattern.compile(totalCostRegex);
        Matcher matcher = pattern.matcher(outputLine);
        if (matcher.find()){
            double totalCost = Double.parseDouble(matcher.group("totalCost"));
            Assertions.assertEquals(Common.numberFormat.format(expectedTotal),
                    Common.numberFormat.format(totalCost));
        }
    }

    public static void testTotalTax(String outputLine, double expectedTotalSalesTax){
        Pattern pattern = Pattern.compile(salesTaxRegex);
        Matcher matcher = pattern.matcher(outputLine);
        if (matcher.find()){
            double totalSalesTax = Double.parseDouble(matcher.group("totalSalesTax"));
            Assertions.assertEquals(Common.numberFormat.format(expectedTotalSalesTax),
                    Common.numberFormat.format(totalSalesTax));
        }
    }
}
