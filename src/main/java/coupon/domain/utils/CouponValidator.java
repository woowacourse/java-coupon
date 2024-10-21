package coupon.domain.utils;

import coupon.domain.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class CouponValidator {

    private static final Logger log = LoggerFactory.getLogger(CouponValidator.class);

    private static final int NAME_MAX_LENGTH = 30;
    private static final int DISCOUNT_MIN_BOUND = 1000;
    private static final int DISCOUNT_MAX_BOUND = 10000;
    private static final int DISCOUNT_AMOUNT_UNIT = 500;
    private static final int MIN_ORDER_MIN_BOUND = 5000;
    private static final int MIN_ORDER_MAX_BOUND = 100000;
    private static final int PERCENTAGE = 100;
    private static final int DISCOUNT_RATE_MIN_BOUND = 3;
    private static final int DISCOUNT_RATE_MAX_BOUND = 20;

    public static void validate(String name, Integer discountAmount, Integer minumumOrderAmount, Category category, LocalDate startDate, LocalDate endDate) {
        validateName(name);
        validateDiscountAmount(discountAmount);
        validateMinOrderAmount(minumumOrderAmount);
        validateDiscountRate(discountAmount, minumumOrderAmount);
        log.info(category + ": 카테고리 설정");
        validateDuration(startDate, endDate);
    }

    private static void validateName(String name) {
        if (name == null || name.isEmpty() || name.length() > NAME_MAX_LENGTH) {
            log.info(name + ": 쿠폰 이름은 비거나 30자를 초과할 수 없습니다.");
            throw new IllegalArgumentException("쿠폰 이름 입력이 잘못되었습니다.");
        }
        log.info(name + ": 이름 설정");
    }

    private static void validateDiscountAmount(Integer discountAmount) {
        if (discountAmount == null || discountAmount < DISCOUNT_MIN_BOUND
                || discountAmount > DISCOUNT_MAX_BOUND || discountAmount % DISCOUNT_AMOUNT_UNIT != 0) {
            log.info(discountAmount + ": 할인 금액은 1,000원 이상 10,000원 이하여야 합니다. (단위: 500원)");
            throw new IllegalArgumentException("할인 금액 입력이 잘못되었습니다.");
        }
        log.info(discountAmount + ": 할인 금액 설정");
    }

    private static void validateMinOrderAmount(Integer minOrderAmount) {
        if (minOrderAmount == null || minOrderAmount < MIN_ORDER_MIN_BOUND || minOrderAmount > MIN_ORDER_MAX_BOUND) {
            log.info(minOrderAmount + ": 최소 주문 금액은 5,000원 이상 100,000원 이하여야 합니다.");
            throw new IllegalArgumentException("최소 주문 금액 입력이 잘못되었습니다.");
        }
        log.info(minOrderAmount + ": 최소 주문 금액 설정");
    }

    private static void validateDiscountRate(Integer discountAmount, Integer minumumOrderAmount) {
        Integer discountRate = discountAmount * PERCENTAGE / minumumOrderAmount;
        if (discountRate < DISCOUNT_RATE_MIN_BOUND || discountRate > DISCOUNT_RATE_MAX_BOUND) {
            log.info(discountRate + ": 할인율은 3% 이상 20% 이하여야 합니다.");
            throw new IllegalArgumentException("올바르지 않은 할인율입니다.");
        }
        log.info(discountRate + ": 계산된 할인율");
    }

    private static void validateDuration(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            log.info("시작일 - " + startDate + ", " + "종료일 - " + endDate + ": 기간 설정이 비어있습니다.");
            throw new IllegalArgumentException("올바르지 않은 기간 설정입니다.");
        }

        if (startDate.isAfter(endDate)) {
            log.info("시작일 - " + startDate + ", " + "종료일 - " + endDate + ": 시작일은 종료일보다 뒤일 수 없습니다.");
            throw new IllegalArgumentException("올바르지 않은 기간 설정입니다.");
        }
        log.info(startDate + "~" + endDate + ": 기간 설정");
    }
}
