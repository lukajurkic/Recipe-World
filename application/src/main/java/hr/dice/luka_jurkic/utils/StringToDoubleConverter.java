package hr.dice.luka_jurkic.utils;

public class StringToDoubleConverter {

    public static Double convertStringWithSlashToDouble(String value) {
        if(value.contains("/")) {
            int slashIndex = value.indexOf("/");
            return Double.parseDouble(value.substring(0, slashIndex)) /
                    Double.parseDouble(value.substring(slashIndex+1));
        }
        return Double.parseDouble(value);
    }
}
