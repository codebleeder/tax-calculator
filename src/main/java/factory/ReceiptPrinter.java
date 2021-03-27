package factory;


import products.Product;
import utilities.Common;

import java.util.List;

public class ReceiptPrinter {
    private List<ProductWithQuantity> inventory;


    public ReceiptPrinter(List<ProductWithQuantity> inventory) {
        this.inventory = inventory;
    }

    public void addProduct(Product product, long quantity){
        inventory.add(new ProductWithQuantity(product, quantity));
    }

    public void generateReceipt(){
        double totalTax = 0.0;
        double total = 0.0;

        for(ProductWithQuantity productWithQuantity: inventory){
            StringBuilder sb = new StringBuilder();

            double cost = productWithQuantity.getQuantity() * productWithQuantity.getProduct().getCost();
            double tax = productWithQuantity.getQuantity() * (productWithQuantity.getProduct().getCost() - productWithQuantity.getProduct().getBaseCost());
            total += cost;
            totalTax += tax;

            sb.append(productWithQuantity.getQuantity())
                    .append(" ")
                    .append(productWithQuantity.getProduct().getName())
                    .append(": ")
                    .append(Common.numberFormat.format(cost));

            System.out.println(sb.toString());
        }
        System.out.println("Sales Taxes: " + Common.numberFormat.format(totalTax));
        System.out.println("Total: " + Common.numberFormat.format(total));
    }
}
