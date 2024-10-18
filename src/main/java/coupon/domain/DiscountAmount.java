package coupon.domain;

import java.util.Objects;

public class DiscountAmount {

    private final int discountAmount;

    public DiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscountAmount that = (DiscountAmount) o;
        return discountAmount == that.discountAmount;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(discountAmount);
    }
}
