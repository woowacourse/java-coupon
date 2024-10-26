package coupon.coupon.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CouponDiscountAmount {
    protected static final Long MIN_DISCOUNT_AMOUNT = 1000L;
    protected static final Long MAX_DISCOUNT_AMOUNT = 10000L;
    protected static final Long INCREMENT_UNIT = 500L;

    @Column(nullable = false)
    private Long discountAmount;

    public CouponDiscountAmount(Long discountAmount) {
        validate(discountAmount);
        this.discountAmount = discountAmount;
    }

    private void validate(Long discountAmount) {
        validateRange(discountAmount);
        validateIncrement(discountAmount);
    }

    private void validateRange(Long discountAmount) {
        if (discountAmount < MIN_DISCOUNT_AMOUNT || discountAmount > MAX_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException("할인 금액은 " + MIN_DISCOUNT_AMOUNT + "원 이상, " + MAX_DISCOUNT_AMOUNT + "원 이하여야 합니다.");
        }
    }

    private void validateIncrement(Long discountAmount) {
        if (discountAmount % INCREMENT_UNIT != 0) {
            throw new IllegalArgumentException("할인 금액은 " + INCREMENT_UNIT + "원 단위로 설정되어야 합니다.");
        }
    }
}
