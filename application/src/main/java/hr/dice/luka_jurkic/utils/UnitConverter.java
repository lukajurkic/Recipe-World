package hr.dice.luka_jurkic.utils;

public class UnitConverter {

    private static final Double POUND_KG_RATIO = 0.45359237;
    private static final Double OUNCE_G_RATIO = 28.34952;
    private static final Double CUP_ML_RATIO = 236.588237;
    private static final Double PECK_L_RATIO = 9.09218;

    public static Double poundToKg(Double pounds) {
        return pounds * POUND_KG_RATIO;
    }

    public static Double ounceToG(Double ounces) {
        return ounces * OUNCE_G_RATIO;
    }

    public static Double cupToMl(Double cups) {
        return cups * CUP_ML_RATIO;
    }

    public static Double peckToL(Double pecks) {
        return pecks * PECK_L_RATIO;
    }
}
