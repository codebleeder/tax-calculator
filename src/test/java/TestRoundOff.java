import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utilities.Common;

public class TestRoundOff {
    @Test
    void testBelowPrecision1(){
        double input = 1.499;
        double expect = 1.5;
        double res = Math.round(input * 20.0) / 20.0;
        Assertions.assertEquals(expect, res);
    }

    @Test
    void testBelowPrecision2(){
        double input = 0.93;
        double expect = 0.95;
        double res = Common.roundOff(input);
        Assertions.assertEquals(expect, res);
    }

    @Test
    void testRoundOffToZero(){
        double input = 0.91;
        double expect = 0.90;
        Assertions.assertEquals(expect, Common.roundOff(input));
    }

    @Test
    void testKeepFive(){
        double input = 0.95;
        double expect = 0.95;
        Assertions.assertEquals(expect, Common.roundOff(input));
    }

    @Test
    void testRoundOff(){
        double input = 0.59;
        double expect = 0.6;
        Assertions.assertEquals(expect, Common.roundOff(input));
    }
}
