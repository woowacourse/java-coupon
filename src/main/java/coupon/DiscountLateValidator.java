package coupon;

import coupon.config.DiscountLateValidationConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@EnableConfigurationProperties(DiscountLateValidationConfig.class)
public class DiscountLateValidator {
    private final BigDecimal minRate;
    private final BigDecimal maxRate;

    public DiscountLateValidator(final DiscountLateValidationConfig config) {
        this.minRate = new BigDecimal(config.minRate());
        this.maxRate = new BigDecimal(config.maxRate());
    }

    public void validate(final BigDecimal rate) {
        if (isUpperMin(rate) && isLowerMax(rate)) {
            return;
        }
        throw new IllegalArgumentException(String.format(String.format("할인율(%s)이 올바르지 않습니다.",rate)));
    }

    private boolean isUpperMin(final BigDecimal rate) {
        return rate.compareTo(minRate) >= 0;
    }
    private boolean isLowerMax(final BigDecimal rate) {
        return rate.compareTo(maxRate) <= 0;
    }
}
