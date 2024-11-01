package coupon.coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiscountAmount {

    private static final int MIN_DISCOUNT_AMOUNT = 0;

    @Column(name = "discount_amount")
    private int discountAmount;

    public DiscountAmount(int discountAmount) {
        validateDiscountAmount(discountAmount);
        this.discountAmount = discountAmount;
    }

    private void validateDiscountAmount(int discountAmount) {
        if (discountAmount < MIN_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException("할인 금액이 %d원 보다 작을 수 없습니다.".formatted(MIN_DISCOUNT_AMOUNT));
        }
    }

    public int getValue() {
        return discountAmount;
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
