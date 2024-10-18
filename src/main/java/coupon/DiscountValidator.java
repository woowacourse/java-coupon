package coupon;

import coupon.config.DiscountValidationConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@EnableConfigurationProperties(DiscountValidationConfig.class)
public class DiscountValidator {
    private final BigDecimal discountMinPrice;
    private final BigDecimal discountMaxPrice;
    private final BigDecimal discountUnit;

    public DiscountValidator(final DiscountValidationConfig discountValidationConfig) {
        this.discountMaxPrice = discountValidationConfig.discountMaxPrice();
        this.discountMinPrice = discountValidationConfig.discountMinPrice();
        this.discountUnit = discountValidationConfig.discountUnit();
    }

    public void validate(final Money money) {
        if (money.isLessThan(discountMinPrice)) {
            throw new IllegalArgumentException(String.format("%s 는 %s 보다 작습니다.", money, discountMinPrice));
        }
        if (money.isGreaterThan(discountMaxPrice)) {
            throw new IllegalArgumentException(String.format("%s 는 %s 보다 큽니다.", money, discountMaxPrice));
        }
        if (money.isNotDivide(discountUnit)) {
            throw new IllegalArgumentException(String.format("%s 는 %s로 나눠지지 않습니다.", money, discountUnit));
        }
    }
}
