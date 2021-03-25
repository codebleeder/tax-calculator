package products;

public class ChocolateBar extends Product{
    public ChocolateBar() {
        setBaseCost(0.85);
        setName("Chocolate Bar");
    }

    public double getCost() {
        return getBaseCost();
    }
}
