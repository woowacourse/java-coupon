package coupon.domain;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class Coupon {

    private static final Logger log = LoggerFactory.getLogger(Coupon.class);

    private final String name;
    private final Integer discountAmount;
    private final Integer minOrderAmount;
    private final Category category;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Coupon(String name, Integer discountAmount, Integer minOrderAmount, Category category, LocalDate startDate, LocalDate endDate) {
        validate(name, discountAmount, minOrderAmount, category, startDate, endDate);
        this.name = name;
        this.discountAmount = discountAmount;
        this.minOrderAmount = minOrderAmount;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isSameDate() {
        return startDate.isEqual(endDate);
    }

    public boolean isGrantable(LocalDateTime requestTime) {
        LocalDateTime startDateTime = LocalDateTime.of(startDate, LocalTime.MIN);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, LocalTime.of(23, 59, 59, 999999000));

        return !requestTime.isBefore(startDateTime) && !requestTime.isAfter(endDateTime);
    }

    private void validate(String name, Integer discountAmount, Integer minumumOrderAmount, Category category, LocalDate startDate, LocalDate endDate) {
        validateName(name);
        validateDiscountAmount(discountAmount);
        validateMinOrderAmount(minumumOrderAmount);
        validateDiscountRate(discountAmount, minumumOrderAmount);
        log.info(category + ": 카테고리 설정");
        validateDuration(startDate, endDate);
    }

    private void validateName(String name) {
        if (name == null || name.isEmpty() || name.length() > 30) {
            log.info(name + ": 쿠폰 이름은 비거나 30자를 초과할 수 없습니다.");
            throw new IllegalArgumentException("쿠폰 이름 입력이 잘못되었습니다.");
        }
        log.info(name + ": 이름 설정");
    }

    private void validateDiscountAmount(Integer discountAmount) {
        if (discountAmount == null || discountAmount < 1000 || discountAmount > 10000 || discountAmount % 500 != 0) {
            log.info(discountAmount + ": 할인 금액은 1,000원 이상 10,000원 이하여야 합니다. (단위: 500원)");
            throw new IllegalArgumentException("할인 금액 입력이 잘못되었습니다.");
        }
        log.info(discountAmount + ": 할인 금액 설정");
    }

    private void validateMinOrderAmount(Integer minOrderAmount) {
        if (minOrderAmount == null || minOrderAmount < 5000 || minOrderAmount > 100000) {
            log.info(minOrderAmount + ": 최소 주문 금액은 5,000원 이상 100,000원 이하여야 합니다.");
            throw new IllegalArgumentException("최소 주문 금액 입력이 잘못되었습니다.");
        }
        log.info(minOrderAmount + ": 최소 주문 금액 설정");
    }

    private void validateDiscountRate(Integer discountAmount, Integer minumumOrderAmount) {
        Integer discountRate = discountAmount * 100 / minumumOrderAmount;
        if (discountRate < 3 || discountRate > 20) {
            log.info(discountRate + ": 할인율은 3% 이상 20% 이하여야 합니다.");
            throw new IllegalArgumentException("올바르지 않은 할인율입니다.");
        }
        log.info(discountRate + ": 계산된 할인율");
    }

    private void validateDuration(LocalDate startDate, LocalDate endDate) {
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
