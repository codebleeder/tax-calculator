package products;

public class Perfume extends Product {
    public Perfume() {
        super(27.99, "Perfume A");
        setBaseCost(27.99);
        setName("Perfume A");
    }

    public double getCost() {
        return getBaseCost();
    }
}
