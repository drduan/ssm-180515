package utils;

import java.math.BigDecimal;

public class BigDecimalUtils {
    public static int compare(BigDecimal a, BigDecimal b) {
        return a == null && b == null ? 0 : (a == null ? - 1 : (b == null ? 1 : a.compareTo(b)));
    }

    public static boolean greater(BigDecimal a, BigDecimal b) {
        return compare(a, b) == 1;
    }

    public static boolean equals(BigDecimal a, BigDecimal b) {
        return compare(a, b) == 0;
    }

    public static boolean less(BigDecimal a, BigDecimal b) {
        return compare(a, b) == - 1;
    }

    public static BigDecimal max(BigDecimal a, BigDecimal b) {
        return compare(a, b) >= 0 ? a : b;
    }

    public static BigDecimal min(BigDecimal a, BigDecimal b) {
        return compare(a, b) >= 0 ? b : a;
    }

    public static BigDecimal add(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }

    public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
        return a.subtract(b);
    }

    public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
        return a.multiply(b);
    }

    public static BigDecimal divide(BigDecimal a, BigDecimal b) {
        return a.divide(b);
    }
}