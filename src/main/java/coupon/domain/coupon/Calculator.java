package coupon.domain.coupon;

public class Calculator {

    public static int calculateDiscountRateOfPercentage(int discountAmount, int minimumOrderAmount) {
        double rateOfDecimal = (double) discountAmount / minimumOrderAmount;
        return  (int) (rateOfDecimal * 100);
    }
}
