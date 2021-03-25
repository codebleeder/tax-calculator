package products;

public class MusicCD extends Product{
    public MusicCD() {
        setBaseCost(14.99);
        setName("Music CD");
    }

    public double getCost() {
        return getBaseCost();
    }
}
