package coupon.domain;

import coupon.exception.CouponException;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
public class MinimumOrderAmount {

    private int minimumOrderAmount;

    public MinimumOrderAmount(int minimumOrderAmount) {
        validateMinimumOrderAmount(minimumOrderAmount);
        this.minimumOrderAmount = minimumOrderAmount;
    }

    private void validateMinimumOrderAmount(int amount) {
        if (amount < 5000 || amount > 100000) {
            throw new CouponException("최소 주문 금액은 5,000원 이상, 100,000원 이하여야 합니다.");
        }
    }
}
