package factory;



import models.Constants;
import products.Product;

import java.util.*;

public class Factory {

    private static final Set<String> taxExemptSet = new HashSet<>(Arrays.asList("chocolate", "chocolates",
            "pill", "pills", "tablet", "tablets", "book", "books"));

    public static Product createBaseProduct(String name, double baseCost){
       return new Product(baseCost, name);
    }

    public static Product applyTaxes(Product product, boolean applySalesTax, boolean isImported){
        if (applySalesTax){
            product.addTax(Constants.SALES_TAX);
        }
        if (isImported) {
            product.addTax(Constants.IMPORT_TAX);
        }
        return product;
    }

    public static Product createProductAndApplyTaxes(String name,
                                                     double baseCost,
                                                     boolean applySalesTax,
                                                     boolean isImported){
        Product product = createBaseProduct(name, baseCost);
        return applyTaxes(product, applySalesTax, isImported);
    }

    public static Product createProductAndApplyTaxes(String name,
                                                     double baseCost){
        Product product = createBaseProduct(name, baseCost);
        boolean applySalesTax = checkForExemptWordsAndApplySalesTax(name);
        boolean isImported = name.toLowerCase(Locale.ROOT).contains("import");
        return applyTaxes(product, applySalesTax, isImported);
    }

    private static boolean checkForExemptWordsAndApplySalesTax(String name){
        List<String> words = Arrays.asList(name.split(" ").clone());
        for(String word: words){
            if (taxExemptSet.contains(word)) {
                return false;
            }
        }
        return true;
    }
}
