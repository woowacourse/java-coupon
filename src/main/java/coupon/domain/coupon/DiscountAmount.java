package coupon.domain.coupon;

import lombok.Getter;

@Getter
public class DiscountAmount {

    private static final int MIN_LIMIT = 1000;
    private static final int MAX_LIMIT = 10000;
    private static final int UNIT = 500;

    private final long discountAmount;

    public DiscountAmount(long discountAmount) {
        validateDiscountAmountRange(discountAmount);
        validateDiscountAmountUnit(discountAmount);
        this.discountAmount = discountAmount;
    }

    private void validateDiscountAmountRange(long discountAmount) {
        if (discountAmount < MIN_LIMIT || discountAmount > MAX_LIMIT) {
            throw new IllegalArgumentException(
                    String.format("정해진 할인 금액 범위를 벗어났습니다. - 범위 : %d원 ~ %d원", MIN_LIMIT, MAX_LIMIT));
        }
    }

    private void validateDiscountAmountUnit(long discountAmount) {
        if (discountAmount % UNIT != 0) {
            throw new IllegalArgumentException(String.format("할인 금액은 정해진 단위로 입력해주세요. - 단위 : %d원", UNIT));
        }
    }
}
