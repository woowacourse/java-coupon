package coupon.coupon.domain;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Discount {

    private static final int MIN = 1000;
    private static final int MAX = 10000;
    private static final int UNIT = 500;

    @Column(nullable = false)
    private int discountValue;

    public Discount(int discountValue) {
        validate(discountValue);
        this.discountValue = discountValue;
    }

    private void validate(int discountValue) {
        validateRange(discountValue);
        validateUnit(discountValue);
    }

    private void validateRange(int discountValue) {
        if (discountValue < MIN || discountValue > MAX) {
            throw new IllegalArgumentException("Discount value must be between " + MIN + " and " + MAX);
        }
    }

    private void validateUnit(int discountValue) {
        if(discountValue %UNIT!=0) {
            throw new IllegalArgumentException("Discount value must be a multiple of " + UNIT);
        }
    }
}
