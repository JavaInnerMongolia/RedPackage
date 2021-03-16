package top.uaian.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtils {

    /**
     * 四舍五入
     */
    public static double round(double num, int scale) {
        return new BigDecimal(num).divide(new BigDecimal(1.0), scale, RoundingMode.HALF_UP).doubleValue();
    }
}
