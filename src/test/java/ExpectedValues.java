public class ExpectedValues {
    private double expectedTotal;
    private double expectedTotalSalesTax;

    public ExpectedValues(double expectedTotal, double expectedTotalSalesTax) {
        this.expectedTotal = expectedTotal;
        this.expectedTotalSalesTax = expectedTotalSalesTax;
    }

    public double getExpectedTotal() {
        return expectedTotal;
    }

    public void setExpectedTotal(double expectedTotal) {
        this.expectedTotal = expectedTotal;
    }

    public double getExpectedTotalSalesTax() {
        return expectedTotalSalesTax;
    }

    public void setExpectedTotalSalesTax(double expectedTotalSalesTax) {
        this.expectedTotalSalesTax = expectedTotalSalesTax;
    }
}
