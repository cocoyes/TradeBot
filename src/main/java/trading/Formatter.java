package trading;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Formatter {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private static final NumberFormat PERCENT_FORMAT = new DecimalFormat("0.000%");

    public static String formatDate(LocalDateTime date) {
        return DATE_TIME_FORMATTER.format(date);
    }

    public static String formatPercent(double percentage) {
        return PERCENT_FORMAT.format(percentage);
    }

    public static String formatDecimal(double decimal) {
        int zeroes = 0;
        String s = String.format("%.12f", decimal).replaceAll("[,.]", "");
        for (char c : s.toCharArray()) {
            if (c == '0') {
                zeroes++;
            } else if (c != '-') {
                break;
            }
        }
        NumberFormat decimalFormat = new DecimalFormat("0." + "0".repeat(3 + zeroes));
        return decimalFormat.format(decimal);
    }
}
