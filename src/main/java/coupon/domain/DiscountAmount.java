package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiscountAmount {

    private static final int MIN_AMOUNT = 1000;
    private static final int MAX_AMOUNT = 10000;
    private static final int AMOUNT_UNIT = 500;

    @Column(nullable = false, name = "discount_amount")
    private Long amount;

    public DiscountAmount(long amount) {
        validate(amount);
        this.amount = amount;
    }

    private void validate(long amount) {
        if (amount < MIN_AMOUNT || amount > MAX_AMOUNT) {
            throw new IllegalArgumentException("할인 금액은 1000원 이상 10000원 이하여야 합니다.");
        }
        if (amount % AMOUNT_UNIT != 0) {
            throw new IllegalArgumentException("할인 금액은 500원 단위로만 입력 가능합니다.");
        }
    }
}
