package coupon.domain;

import java.time.LocalDateTime;
import java.util.Objects;
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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    private Integer discountAmount;

    @Column(nullable = false)
    private Integer minOrderAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    public Coupon(String name, Integer discountAmount, Integer minOrderAmount, Category category, LocalDateTime startDate, LocalDateTime endDate) {
        validateCoupon(name, discountAmount, minOrderAmount, category, startDate, endDate);
        this.name = name;
        this.discountAmount = discountAmount;
        this.minOrderAmount = minOrderAmount;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validateCoupon(String name, Integer discountAmount, Integer minOrderAmount, Category category, LocalDateTime startDate, LocalDateTime endDate) {
        validateName(name);
        validateDiscount(discountAmount);
        validateDiscountRate(discountAmount, minOrderAmount);
        validateMinOrderAmount(minOrderAmount);
        validateCategory(category);
        validateDates(startDate, endDate);
    }

    private void validateName(String name) {
        validateIsNullAndEmpty(name, "쿠폰 이름은 반드시 존재해야 합니다.");
        if (name.length() > 30) {
            throw new IllegalArgumentException("쿠폰 이름은 최대 30자 이하여야 합니다.");
        }
    }

    private void validateIsNullAndEmpty(String value, String exceptionMessage) {
        if (Objects.isNull(value) || value.isEmpty()) {
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    private void validateIsNull(Object value, String exceptionMessage) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    private void validateDiscount(Integer discountAmount) {
        validateIsNull(discountAmount, "할인 금액은 반드시 존재해야 합니다.");
        if (discountAmount < 1000 || discountAmount > 10000) {
            throw new IllegalArgumentException("할인 금액은 1,000원 이상 10,000원 이하여야 합니다.");
        }
        if (discountAmount % 500 != 0) {
            throw new IllegalArgumentException("할인 금액은 500원 단위로 설정할 수 있습니다.");
        }
    }

    private void validateDiscountRate(Integer discountAmount, Integer minOrderAmount) {
        validateIsNull(discountAmount, "할인 금액은 반드시 존재해야 합니다.");
        validateIsNull(minOrderAmount, "최소 주문 금액은 반드시 존재해야 합니다.");
        int calculatedDiscountRate = (int) Math.floor(((double) discountAmount / minOrderAmount) * 100);
        if (calculatedDiscountRate < 3 || calculatedDiscountRate > 20) {
            throw new IllegalArgumentException("할인율은 3% 이상 20% 이하여야 합니다.");
        }
    }

    private void validateMinOrderAmount(Integer minOrderAmount) {
        validateIsNull(minOrderAmount, "최소 주문 금액은 반드시 존재해야 합니다.");
        if (minOrderAmount < 5000 || minOrderAmount > 100000) {
            throw new IllegalArgumentException("최소 주문 금액은 5,000원 이상 100,000원 이하여야 합니다.");
        }
    }

    private void validateCategory(Category category) {
        validateIsNull(category, "카테고리는 반드시 선택해야 합니다.");
    }

    private void validateDates(LocalDateTime startDate, LocalDateTime endDate) {
        validateIsNull(startDate, "발급 시작일은 반드시 존재해야 합니다.");
        validateIsNull(endDate, "발급 종료일은 반드시 존재해야 합니다.");
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("종료일은 시작일보다 이후여야 합니다.");
        }
    }
}
