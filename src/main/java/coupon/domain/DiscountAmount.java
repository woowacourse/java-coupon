package coupon.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class DiscountAmount {

    private static final int MIN_VALUE = 1_000;
    private static final int MAX_VALUE = 10_000;
    private static final int UNIT = 500;

    private final int value;

    public DiscountAmount(final int value) {
        validate(value);
        this.value = value;
    }

    private void validate(final int value) {
        if (value < MIN_VALUE || MAX_VALUE < value) {
            throw new IllegalArgumentException(
                    String.format("할인 금액은 최소 %s, 최대 %s 입니다. 요청 값: %s", MIN_VALUE, MAX_VALUE, value));
        }

        if (value % UNIT != 0) {
            throw new IllegalArgumentException(String.format("할인 금액 단위는 %s여야 합니다. 요청 값: %s", UNIT, value));
        }
    }
}
