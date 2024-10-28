package coupon.domain;

import java.util.Objects;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MinOrderAmount {

    private static final int MIN_AMOUNT = 5_000;
    private static final int MAX_AMOUNT = 100_000;

    private final int amount;

    public MinOrderAmount(final int amount) {
        validate(amount);
        this.amount = amount;
    }

    private void validate(final int amount) {
        if (amount < MIN_AMOUNT || MAX_AMOUNT < amount) {
            throw new IllegalArgumentException(
                    String.format("최소 주문 금액은 %s 이상 %s 이하여야 합니다. 입력값: %s", MIN_AMOUNT, MAX_AMOUNT, amount));
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final MinOrderAmount that)) {
            return false;
        }
        return getAmount() == that.getAmount();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getAmount());
    }
}
