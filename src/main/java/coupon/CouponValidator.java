package coupon;

import coupon.domain.Coupon;
import java.time.LocalDateTime;

public class CouponValidator {


    public static void validate(Coupon coupon) {
        validateName(coupon.getName());
        validateDiscountAmount(coupon.getDiscountAmount());
        validateMinimumOrderAmount(coupon.getMinimumOrderAmount());
        validateDiscountRate(coupon.getDiscountAmount(), coupon.getMinimumOrderAmount());
        validateEndDate(coupon.getStartDate(), coupon.getEndDate());
    }

    private static void validateName(String name) {
        if (name == null || name.isEmpty() || name.length() > 30) {
            throw new IllegalArgumentException("이름은 반드시 존재해야 하며, 최대 30자 이하여야 합니다");
        }
    }

    private static void validateDiscountAmount(int discountAmount) {
        if (discountAmount < 1000 || discountAmount > 10000 || discountAmount % 500 != 0) {
            throw new IllegalArgumentException("할인 금액은 1,000원 이상, 10,000원 이하이며 500원 단위로 설정해야 합니다");
        }
    }

    private static void validateMinimumOrderAmount(int minimumOrderAmount) {
        if (minimumOrderAmount < 5000 || minimumOrderAmount > 100000) {
            throw new IllegalArgumentException("최소 주문 금액은 5,000원 이상, 100,000원 이하여야 합니다");
        }
    }

    private static void validateDiscountRate(int discountAmount, int minimumOrderAmount) {
        double discountRate = Math.floor((double) discountAmount / minimumOrderAmount * 100);
        if (discountRate < 3 || discountRate > 20) {
            throw new IllegalArgumentException("할인율은 3% 이상, 20% 이하여야 합니다");
        }
    }

    private static void validateEndDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작일은 종료일보다 이전이어야 합니다");
        }
    }
}
