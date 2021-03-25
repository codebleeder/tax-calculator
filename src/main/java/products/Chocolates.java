package products;

public class Chocolates extends Product{
    public Chocolates() {
        setBaseCost(10);
        setName("Chocolate");
    }

    public double getCost() {
        return getBaseCost();
    }
}
