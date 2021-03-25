package products;

public class Book extends Product{
    public Book() {
        setBaseCost(12.49);
        setName("Book");
    }

    public double getCost() {
        return getBaseCost();
    }
}
