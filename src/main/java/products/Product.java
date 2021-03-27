package products;

import utilities.Common;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private double baseCost;
    private String name;
    private List<Double> taxes = new ArrayList<>();

    public Product(double baseCost, String name) {
        this.baseCost = baseCost;
        this.name = name;
    }

    public double getBaseCost() {
        return baseCost;
    }

    public String getName() {
        return name;
    }

    public double getCost(){
        double cost = getBaseCost();
        for(double tax: getTaxes()){
            cost += Common.roundOff(getBaseCost() * tax);
        }
        return cost;
    };

    public void setBaseCost(double baseCost) {
        this.baseCost = baseCost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Double> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<Double> taxes) {
        this.taxes = taxes;
    }

    public void addTax(double tax){
        taxes.add(tax);
    }
}
