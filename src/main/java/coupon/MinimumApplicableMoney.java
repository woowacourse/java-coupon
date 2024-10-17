package coupon;

import java.math.BigDecimal;

record MinimumApplicableMoney(BigDecimal value) {

    private static final BigDecimal MAXIMUM_APPLICABLE_MONEY = new BigDecimal("100000");
    private static final BigDecimal MINIMUM_APPLICABLE_MONEY = new BigDecimal("5000");
    private static final String MAXIMUM_LIMIT_MESSAGE = String.format(
            "해당 쿠폰의 최대 적용 가능 금액은 %s원 이하입니다.",
            MAXIMUM_APPLICABLE_MONEY
    );
    private static final String MINIMUM_LIMIT_MESSAGE = String.format(
            "해당 쿠폰의 최소 적용 가능 금액은 %s원 이상입니다.",
            MINIMUM_APPLICABLE_MONEY

    );

    MinimumApplicableMoney {
        if (value.compareTo(MINIMUM_APPLICABLE_MONEY) < 0) {
            throw new IllegalArgumentException(MINIMUM_LIMIT_MESSAGE);
        }

        if (value.compareTo(MAXIMUM_APPLICABLE_MONEY) > 0) {
            throw new IllegalArgumentException(MAXIMUM_LIMIT_MESSAGE);
        }
    }
}
