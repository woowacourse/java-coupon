package coupon;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Money(BigDecimal amount) {
    public static Money from(final long amount) {
        return new Money(BigDecimal.valueOf(amount));
    }

    public boolean isLessThan(final BigDecimal amount) {
        return this.amount.compareTo(amount) < 0;
    }

    public boolean isGreaterThan(final BigDecimal amount) {
        return this.amount.compareTo(amount) > 0;
    }

    public boolean isLessOrEqualThan(final BigDecimal amount) {
        return this.amount.compareTo(amount) <= 0;
    }

    public boolean isGreaterOrEqualThan(final BigDecimal amount) {
        return this.amount.compareTo(amount) >= 0;
    }

    public boolean isDivide(final BigDecimal amount) {
        return this.amount.remainder(amount)
                .compareTo(BigDecimal.ZERO) == 0;
    }

    public boolean isNotDivide(final BigDecimal amount) {
        return !isDivide(amount);
    }

    public BigDecimal divide(final Money amount) {
        return this.amount.divide(amount.amount, RoundingMode.DOWN);
    }
}
