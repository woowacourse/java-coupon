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

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Coupon {

    private static final int MAXIMUM_NAME_LENGTH = 30;
    private static final int MINIMUM_DISCOUNT_AMOUNT = 1_000;
    private static final int MAXIMUM_DISCOUNT_AMOUNT = 10_000;
    private static final int DISCOUNT_AMOUNT_UNIT = 500;
    private static final int MINIMUM_MINIMUM_ORDER_AMOUNT = 5_000;
    private static final int MAXIMUM_MINIMUM_ORDER_AMOUNT = 100_000;
    public static final int MINIMUM_DISCOUNT_RATE = 3;
    public static final int MAXIMUM_DISCOUNT_RATE = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = MAXIMUM_NAME_LENGTH)
    private String name;

    @Column(nullable = false)
    private Integer discountAmount;

    @Column(nullable = false)
    private Integer minimumOrderAmount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    public Coupon(String name, Integer discountAmount, Integer minimumOrderAmount, Category category) {
        validate(name, discountAmount, minimumOrderAmount);
        this.name = name;
        this.discountAmount = discountAmount;
        this.minimumOrderAmount = minimumOrderAmount;
        this.category = category;
    }

    private void validate(String name, Integer discountAmount, Integer minimumOrderAmount) {
        validateName(name);
        validateDiscountAmount(discountAmount);
        validateMinimumOrderAmount(minimumOrderAmount);
        validateDiscountRate(discountAmount, minimumOrderAmount);
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("쿠폰 이름은 반드시 존재해야 합니다.");
        }
        if (name.length() > MAXIMUM_NAME_LENGTH) {
            throw new IllegalArgumentException("쿠폰 이름의 길이는 최대 30자 이하여야 합니다.");
        }
    }

    private void validateDiscountAmount(Integer discountAmount) {
        if (discountAmount < MINIMUM_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException("할인 금액은 1,000원 이상이어야 합니다.");
        }
        if (discountAmount > MAXIMUM_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException("할인 금액은 10,000원 이하여야 합니다.");
        }
        if (discountAmount % DISCOUNT_AMOUNT_UNIT != 0) {
            throw new IllegalArgumentException("할인 금액은 500원 단위로 설정해야 합니다.");
        }
    }

    private void validateMinimumOrderAmount(Integer minimumOrderAmount) {
        if (minimumOrderAmount < MINIMUM_MINIMUM_ORDER_AMOUNT) {
            throw new IllegalArgumentException("최소 주문 금액은 5,000원 이상이어야 합니다.");
        }
        if (minimumOrderAmount > MAXIMUM_MINIMUM_ORDER_AMOUNT) {
            throw new IllegalArgumentException("최소 주문 금액은 100,000원 이하여야 합니다.");
        }
    }

    private void validateDiscountRate(Integer discountAmount, Integer minimumOrderAmount) {
        double discountRate = (discountAmount / (double) minimumOrderAmount) * 100;
        if (discountRate < MINIMUM_DISCOUNT_RATE || discountRate > MAXIMUM_DISCOUNT_RATE) {
            throw new IllegalArgumentException("잘못된 할인율입니다.");
        }
    }
}
