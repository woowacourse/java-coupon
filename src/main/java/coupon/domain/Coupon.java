package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Coupon {

    private static final int MAX_COUPON_NAME = 30;
    private static final int MIN_ORDER_AMOUNT = 5000;
    private static final int MAX_ORDER_AMOUNT = 100000;
    private static final int MIN_DISCOUNT_AMOUNT = 1000;
    private static final int MAX_DISCOUNT_AMOUNT = 10000;
    private static final int DISCOUNT_AMOUNT_UNIT = 500;
    private static final int MIN_DISCOUNT_RATE = 3;
    private static final int MAX_DISCOUNT_RATE = 20;
    private static final int PERCENT_FACTOR = 100;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    private int minimumOrderAmount;

    private int discountAmount;

    private int discountRate;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    private LocalDateTime issueStartDate;

    private LocalDateTime issueEndDate;

    public Coupon(String name, int minimumOrderAmount, int discountAmount, Category category,
                  LocalDateTime issueStartDate, LocalDateTime issueEndDate) {
        validateName(name);
        validateMinimumOrderAmount(minimumOrderAmount);
        validateDiscountAmount(discountAmount);
        validateDiscountRate(discountAmount, minimumOrderAmount);
        validateIssuePeriod(issueStartDate, issueEndDate);

        this.name = name;
        this.minimumOrderAmount = minimumOrderAmount;
        this.discountAmount = discountAmount;
        this.discountRate = calculateDiscountRate(discountAmount, minimumOrderAmount);
        this.category = category;
        this.issueStartDate = issueStartDate;
        this.issueEndDate = issueEndDate;
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("쿠폰 이름은 필수입니다.");
        }
        if (name.length() > MAX_COUPON_NAME) {
            throw new IllegalArgumentException("쿠폰 이름은 30자 이하여야 합니다.");
        }
    }

    private void validateMinimumOrderAmount(int minimumOrderAmount) {
        if (minimumOrderAmount < MIN_ORDER_AMOUNT || minimumOrderAmount > MAX_ORDER_AMOUNT) {
            throw new IllegalArgumentException("최소 주문 금액은 5,000원 이상, 100,000원 이하여야 합니다.");
        }
    }

    private void validateDiscountAmount(int discountAmount) {
        if (discountAmount < MIN_DISCOUNT_AMOUNT || discountAmount > MAX_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException("할인 금액은 1,000원 이상, 10,000원 이하여야 합니다.");
        }
        if (discountAmount % DISCOUNT_AMOUNT_UNIT != 0) {
            throw new IllegalArgumentException("할인 금액은 500원 단위로 설정해야 합니다.");
        }
    }


    private void validateDiscountRate(int discountAmount, int minimumOrderAmount) {
        int discountRate = calculateDiscountRate(discountAmount, minimumOrderAmount);
        if (discountRate < MIN_DISCOUNT_RATE || discountRate > MAX_DISCOUNT_RATE) {
            throw new IllegalArgumentException("할인율은 3% 이상, 20% 이하여야 합니다.");
        }
    }

    private int calculateDiscountRate(int discountAmount, int minimumOrderAmount) {
        return (discountAmount * PERCENT_FACTOR) / minimumOrderAmount;
    }

    private void validateIssuePeriod(LocalDateTime issueStartDate, LocalDateTime issueEndDate) {
        if (issueStartDate == null || issueEndDate == null) {
            throw new IllegalArgumentException("발급 기간의 시작일과 종료일은 필수입니다.");
        }
        if (!issueStartDate.isBefore(issueEndDate) && !issueStartDate.isEqual(issueEndDate)) {
            throw new IllegalArgumentException("발급 시작일은 종료일보다 이전이어야 합니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coupon coupon = (Coupon) o;
        return Objects.equals(id, coupon.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
