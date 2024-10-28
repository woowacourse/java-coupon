package coupon.domain.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Discount {

    private static final long MINIMUM_DISCOUNT_AMOUNT = 1000;
    private static final long MAXIMUM_DISCOUNT_AMOUNT = 10000;
    private static final long DISCOUNT_UNIT = 500;

    @Column(name = "DISCOUNT_AMOUNT", nullable = false)
    private long amount;

    public Discount(final long amount) {
        validate(amount);
        this.amount = amount;
    }

    private void validate(final long amount) {
        validateMinAmount(amount);
        validateMaxAmount(amount);
        validateUnit(amount);
    }

    private void validateMinAmount(final long amount) {
        if (amount < MINIMUM_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException("할인 금액 최소 액은 %d 이상이어야 합니다.".formatted(MINIMUM_DISCOUNT_AMOUNT));
        }
    }

    private void validateMaxAmount(final long amount) {
        if (amount > MAXIMUM_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException("할인 금액은 최대 액은 %d 이하이어야 합니다.".formatted(MAXIMUM_DISCOUNT_AMOUNT));
        }
    }

    private void validateUnit(final long amount) {
        if (amount % DISCOUNT_UNIT != 0) {
            throw new IllegalArgumentException("할인 금액은 %d원 단위입니다.".formatted(DISCOUNT_UNIT));
        }
    }
}
