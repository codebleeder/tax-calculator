package products;

public abstract class Product {
    private double baseCost;
    private String name;

    public double getBaseCost() {
        return baseCost;
    }

    public String getName() {
        return name;
    }

    public abstract double getCost();

    public void setBaseCost(double baseCost) {
        this.baseCost = baseCost;
    }

    public void setName(String name) {
        this.name = name;
    }
}
