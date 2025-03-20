package utility;

import java.math.BigDecimal;

public class DataUtil {

    private DataUtil() {

    }

    public static double convertPriceStringToDouble(String priceString) {
        return Double.parseDouble(priceString.replaceAll("[^\\d.]", ""));
    }

    public static BigDecimal convertPriceStringToBigDecimal(String priceString) {
        return new BigDecimal(priceString.replaceAll("[^\\d.]", ""));
    }
}
