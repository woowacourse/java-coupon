package coupon.domain.coupon;

import coupon.domain.BaseEntity;
import coupon.exception.CouponException;
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

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends BaseEntity {

    private static final int MIN_NAME_LENGTH = 30;
    private static final int MIN_DISCOUNT_AMOUNT = 1000;
    private static final int MAX_DISCOUNT_AMOUNT = 10000;
    private static final int DISCOUNT_AMOUNT_UNIT = 500;
    private static final int MIN_MINIMUM_ORDER_PRICE = 5000;
    private static final int MAX_MINIMUM_ORDER_PRICE = 100000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int discountAmount;

    private int minimumOrderPrice;

    @Enumerated(EnumType.STRING)
    private CouponCategory couponCategory;

    public Coupon(
            String name,
            int discountAmount,
            int minimumOrderPrice,
            CouponCategory couponCategory,
            LocalDateTime issueStartDate,
            LocalDateTime issueEndDate
    ) {
        this.name = validateName(name);
        this.discountAmount = validateDiscountAmount(discountAmount);
        this.minimumOrderPrice = validateMinimumOrderPrice(minimumOrderPrice);
        this.couponCategory = couponCategory;
        validateIssueDate(issueStartDate, issueEndDate);
    }

    public String validateName(String name) {
        if (name == null) {
            throw new CouponException("쿠폰 이름이 null 값입니다.");
        }
        if (!name.isBlank() && name.length() <= MIN_NAME_LENGTH) {
            return name;
        }
        throw new CouponException("쿠폰 이름은 %s자 이하가 되어야 합니다.".formatted(MIN_NAME_LENGTH));
    }

    public int validateDiscountAmount(int discountAmount) {
        validateRange(discountAmount);
        validateUnit(discountAmount);
        return discountAmount;
    }

    private void validateRange(int discountAmount) {
        if (discountAmount < MIN_DISCOUNT_AMOUNT || discountAmount > MAX_DISCOUNT_AMOUNT) {
            throw new CouponException(
                    "할인 금액은 %s원 이상, %s원 이하입니다.".formatted(MIN_DISCOUNT_AMOUNT, MAX_DISCOUNT_AMOUNT)
            );
        }
    }

    private void validateUnit(int discountAmount) {
        if (discountAmount % DISCOUNT_AMOUNT_UNIT != 0) {
            throw new CouponException("할인 금액은 %s원 단위로 설정할 수 있습니다.".formatted(DISCOUNT_AMOUNT_UNIT));
        }
    }

    private int validateMinimumOrderPrice(int minimumOrderPrice) {
        if (minimumOrderPrice < MIN_MINIMUM_ORDER_PRICE || minimumOrderPrice > MAX_MINIMUM_ORDER_PRICE) {
            throw new CouponException(
                    "최소 주문 금액은 %s원 이상, %s원 이하입니다.".formatted(MIN_MINIMUM_ORDER_PRICE, MAX_MINIMUM_ORDER_PRICE)
            );
        }
        return minimumOrderPrice;
    }

    private void validateIssueDate(LocalDateTime issueStartDate, LocalDateTime issueEndDate) {
        if (issueStartDate == null || issueEndDate == null) {
            throw new CouponException("발급 시작일과 발급 종료일은 빈 값이 될 수 없습니다.");
        }
        if (issueStartDate.isAfter(issueEndDate)) {
            throw new CouponException(
                    "발급 시작일(%s)은 발급 종료일(%s)보다 이전이어야 합니다.".formatted(issueStartDate, issueEndDate)
            );
        }
    }
}
