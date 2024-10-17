package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
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
        if (name.length() > 30) {
            throw new IllegalArgumentException("쿠폰 이름의 길이는 최대 30자 이하여야 합니다.");
        }
    }

    private void validateDiscountAmount(Integer discountAmount) {
        if (discountAmount < 1_000) {
            throw new IllegalArgumentException("할인 금액은 1,000원 이상이어야 합니다.");
        }
        if (discountAmount > 10_000) {
            throw new IllegalArgumentException("할인 금액은 10,000원 이하여야 합니다.");
        }
        if (discountAmount % 500 != 0) {
            throw new IllegalArgumentException("할인 금액은 500원 단위로 설정해야 합니다.");
        }
    }

    private void validateMinimumOrderAmount(Integer minimumOrderAmount) {
        if (minimumOrderAmount < 5_000) {
            throw new IllegalArgumentException("최소 주문 금액은 5,000원 이상이어야 합니다.");
        }
        if (minimumOrderAmount > 100_000) {
            throw new IllegalArgumentException("최소 주문 금액은 100,000원 이하여야 합니다.");
        }
    }

    private void validateDiscountRate(Integer discountAmount, Integer minimumOrderAmount) {
        double discountRate = (discountAmount / (double) minimumOrderAmount) * 100;
        if (discountRate < 3 || discountRate > 20) {
            throw new IllegalArgumentException("잘못된 할인율입니다.");
        }
    }
}
