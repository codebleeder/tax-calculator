package models;

public class InputData {
    private long quantity;
    private String name;
    private double baseCost;

    public InputData(long quantity, String name, double baseCost) {
        this.quantity = quantity;
        this.name = name;
        this.baseCost = baseCost;
    }

    public long getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public double getBaseCost() {
        return baseCost;
    }
}
