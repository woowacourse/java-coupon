package coupon.common.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import lombok.Getter;

@Getter
public class Money {

    private final BigDecimal amount;

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public static Money wons(long amount) {
        return new Money(BigDecimal.valueOf(amount));
    }

    public Money plus(Money money) {
        return new Money(amount.add(money.amount));
    }

    public Money minus(Money money) {
        return new Money(amount.subtract(money.amount));
    }

    public Money times(double percent) {
        return new Money(amount.multiply(BigDecimal.valueOf(percent)));
    }

    public int ofRatio(Money total) {
        return amount.divide(total.amount, 2, RoundingMode.FLOOR)
                .multiply(BigDecimal.valueOf(100))
                .intValue();
    }

    public boolean isMultipleOf(Money money) {
        return amount.remainder(money.amount).equals(BigDecimal.ZERO);
    }

    public boolean isLessThan(Money other) {
        return amount.compareTo(other.amount) < 0;
    }

    public boolean isGreaterThanOrEqual(Money other) {
        return amount.compareTo(other.amount) >= 0;
    }

    public long longValue() {
        return amount.longValue();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Money other)) {
            return false;
        }

        return Objects.equals(amount.doubleValue(), other.amount.doubleValue());
    }

    public int hashCode() {
        return amount.hashCode();
    }

    public String toString() {
        return amount.toString() + "원";
    }
}
