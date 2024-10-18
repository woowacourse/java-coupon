package coupon.domain.coupon;

import lombok.Getter;

@Getter
public class MinOrderAmount {

    private static final int MIN_LIMIT = 5000;
    private static final int MAX_LIMIT = 100000;

    private final long minOrderAmount;

    public MinOrderAmount(long minOrderAmount) {
        validateMinOrderAmountRange(minOrderAmount);
        this.minOrderAmount = minOrderAmount;
    }

    private void validateMinOrderAmountRange(long minOrderAmount) {
        if (minOrderAmount < MIN_LIMIT || minOrderAmount > MAX_LIMIT) {
            throw new IllegalArgumentException(
                    String.format("정해진 최소 주문 금액 범위를 벗어났습니다. - 범위 : %d원 ~ %d원", MIN_LIMIT, MAX_LIMIT));
        }
    }
}
