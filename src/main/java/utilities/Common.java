package utilities;

import java.text.DecimalFormat;

public class Common {
    public static final DecimalFormat numberFormat = new DecimalFormat("#0.00");
    public static double roundOff(double input) {
        return Math.round(input * 20.0) / 20.0;
    }
}
