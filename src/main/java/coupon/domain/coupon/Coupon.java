package coupon.domain.coupon;

import coupon.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "coupon")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends BaseEntity {

    private static final int MAX_NAME_LENGTH = 30;
    private static final int MIN_DISCOUNT_AMOUNT = 1000;
    private static final int MAX_DISCOUNT_AMOUNT = 100000;
    private static final int DISCOUNT_UNIT = 500;
    private static final int MIN_DISCOUNT_RATE = 3;
    private static final int MAX_DISCOUNT_RATE = 20;
    private static final int MIN_MIN_ORDER_AMOUNT = 5000;
    private static final int MAX_MIN_ORDER_AMOUNT = 100000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int discountAmount;

    private int discountRate;

    private int minimumOrderAmount;

    @Enumerated(EnumType.STRING)
    private Category category;

    public Coupon(
            Long id, String name,
            int discountAmount, int minimumOrderAmount, Category category,
            LocalDateTime startDate, LocalDateTime endDate
    ) {
        validate(name, discountAmount, minimumOrderAmount, startDate, endDate);
        int rate = calcDiscountRate(discountAmount, minimumOrderAmount);
        this.id = id;
        this.name = name;
        this.discountAmount = discountAmount;
        this.discountRate = rate;
        this.minimumOrderAmount = minimumOrderAmount;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validate(String name, int discountAmount, int minimumOrderAmount, LocalDateTime startDate,
                          LocalDateTime endDate) {
        validateCouponName(name);
        validateDiscountAmount(discountAmount);
        validateMinimumOrderAmount(minimumOrderAmount);
        validateDate(startDate, endDate);
    }

    public void validateCouponName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("쿠폰 이름이 비어 있습니다.");
        }
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("쿠폰 이름은 " + MAX_NAME_LENGTH + "자 이하여야 합니다.");
        }
    }

    private void validateDiscountAmount(int discountAmount) {
        if (discountAmount < MIN_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException("할인 금액은 " + MIN_DISCOUNT_AMOUNT + "원 이상이어야 합니다.");
        }
        if (discountAmount > MAX_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException("할인 금액은 " + MAX_DISCOUNT_AMOUNT + "원 이하여야 합니다.");
        }
        if (discountAmount % DISCOUNT_UNIT != 0) {
            throw new IllegalArgumentException("할인 금액은 " + DISCOUNT_UNIT + "원 단위로 입력해야 합니다.");
        }
    }

    private int calcDiscountRate(int discountAmount, int minimumOrderAmount) {
        int rate = (int) ((double) discountAmount / minimumOrderAmount * 100);
        if (rate < MIN_DISCOUNT_RATE) {
            throw new IllegalArgumentException("할인율은 " + MIN_DISCOUNT_RATE + "% 이상이어야 합니다.");
        }
        if (rate > MAX_DISCOUNT_RATE) {
            throw new IllegalArgumentException("할인율은 " + MAX_DISCOUNT_RATE + "% 이하여야 합니다.");
        }
        return rate;
    }

    private void validateMinimumOrderAmount(int minimumOrderAmount) {
        if (minimumOrderAmount < MIN_MIN_ORDER_AMOUNT) {
            throw new IllegalArgumentException("최소 주문 금액은 " + MIN_MIN_ORDER_AMOUNT + "원 이상이어야 합니다.");
        }
        if (minimumOrderAmount > MAX_MIN_ORDER_AMOUNT) {
            throw new IllegalArgumentException("최소 주문 금액은 " + MAX_MIN_ORDER_AMOUNT + "원 이하여야 합니다.");
        }
    }

    private void validateDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작일은 종료일 이전이어야 합니다.");
        }
    }
}
