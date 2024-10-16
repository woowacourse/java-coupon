package coupon.coupon.domain;

import coupon.coupon.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Coupon extends BaseEntity {

    private static final int MAX_NAME_LENGTH = 30;
    private static final int MIN_DISCOUNT_AMOUNT = 1000;
    private static final int MAX_DISCOUNT_AMOUNT = 10000;
    private static final int DISCOUNT_UNIT = 500;
    private static final int MIN_ORDER_AMOUNT = 5000;
    private static final int MAX_ORDER_AMOUNT = 100000;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    @NonNull
    private String name;

    private int discountAmount;

    private int minOrderAmount;

    @Enumerated(EnumType.STRING)
    private Category category;

    public Coupon(
            Long id, String name,
            int discountAmount, int minOrderAmount, Category category,
            LocalDateTime startDate, LocalDateTime endDate
    ) {
        validateName(name);
        validateDiscountAmount(discountAmount);
        validateMinOrderAmount(minOrderAmount);
        validateEndDate(startDate, endDate);
        this.id = id;
        this.name = name;
        this.discountAmount = discountAmount;
        this.minOrderAmount = minOrderAmount;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("쿠폰 이름은 반드시 존재해야 합니다.");
        }
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("쿠폰 이름은 30자 이하여야 합니다.");
        }
    }

    private void validateDiscountAmount(int discountAmount) {
        if (discountAmount < MIN_DISCOUNT_AMOUNT || discountAmount > MAX_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException("할인 금액은 1,000원 이상 10,000원 이하여야 합니다.");
        }
        if (discountAmount % DISCOUNT_UNIT != 0) {
            throw new IllegalArgumentException("할인 금액은 500원 단위여야 합니다.");
        }
    }

    public void validateMinOrderAmount(int minOrderAmount) {
        if (minOrderAmount < MIN_ORDER_AMOUNT) {
            throw new IllegalArgumentException("최소 주문 금액은 5,000원 이상이어야 합니다.");
        }
        if (minOrderAmount > MAX_ORDER_AMOUNT) {
            throw new IllegalArgumentException("최소 주문 금액은 100,000원 이하여야 합니다.");
        }
    }

    public void validateEndDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("종료일은 시작일 이후여야 합니다.");
        }
    }
}
