package products;

public class HeadachePills extends Product{
    public HeadachePills() {
        setBaseCost(9.75);
        setName("Headache Pills");
    }

    public double getCost() {
        return getBaseCost();
    }
}
