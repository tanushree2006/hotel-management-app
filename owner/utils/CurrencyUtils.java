package owner.utils;
public class CurrencyUtils {
    // Update the rate as per latest value; example: 1 USD = 83 INR
    public static final double RUPEES_PER_DOLLAR = 83.0;

    // Converts rupees to dollars, returns formatted string with $ sign and comma separators
    public static String rupeesToDollars(double rupees) {
        double dollars = rupees / RUPEES_PER_DOLLAR;
        return String.format("$%,.2f", dollars);
    }
}