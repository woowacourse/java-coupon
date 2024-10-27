package coupon.domain.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiscountAmount {

    private static final int MIN_AMOUNT = 1_000;
    private static final int MAX_AMOUNT = 10_000;
    private static final int UNIT = 500;

    @Column(name = "discount_amount", nullable = false)
    private int value;

    public DiscountAmount(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        validateMin(value);
        validateMax(value);
        validateUnit(value);
    }

    private void validateMin(int value) {
        if (value < MIN_AMOUNT) {
            throw new IllegalArgumentException("할인 금액은 " + MIN_AMOUNT + "원 이상이어야 합니다.");
        }
    }

    private void validateMax(int value) {
        if (value > MAX_AMOUNT) {
            throw new IllegalArgumentException("할인 금액은 " + MAX_AMOUNT + "원 이하여야 합니다.");
        }
    }

    private void validateUnit(int value) {
        if (value % UNIT != 0) {
            throw new IllegalArgumentException("할인 금액은 " + UNIT + "원 단위로 설정할 수 있습니다.");
        }
    }
}
