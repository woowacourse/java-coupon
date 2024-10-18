package coupon.coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CouponMinimumOrderAmount {
    protected static final Long MIN_MINIMUM_ORDER_AMOUNT = 5000L;
    protected static final Long MAX_MINIMUM_ORDER_AMOUNT = 100000L;

    @Column(nullable = false)
    private Long minimumOrderAmount;

    public CouponMinimumOrderAmount(Long minimumOrderAmount) {
        validate(minimumOrderAmount);
        this.minimumOrderAmount = minimumOrderAmount;
    }

    private void validate(Long minimumOrderAmount) {
        validateRange(minimumOrderAmount);
    }

    private void validateRange(Long minimumOrderAmount) {
        if (minimumOrderAmount < MIN_MINIMUM_ORDER_AMOUNT || minimumOrderAmount > MAX_MINIMUM_ORDER_AMOUNT) {
            throw new IllegalArgumentException("최소 주문 금액은 " + MIN_MINIMUM_ORDER_AMOUNT + "원 이상, " + MAX_MINIMUM_ORDER_AMOUNT + "원 이하여야 합니다.");
        }
    }
}
