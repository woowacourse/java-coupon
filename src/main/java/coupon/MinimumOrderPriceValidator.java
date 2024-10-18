package coupon;

import coupon.config.MinimumOrderPriceConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@EnableConfigurationProperties(MinimumOrderPriceConfig.class)
public class MinimumOrderPriceValidator {
    private final BigDecimal lowerPrice;
    private final BigDecimal upperPrice;

    public MinimumOrderPriceValidator(final MinimumOrderPriceConfig config) {
        this.lowerPrice = config.lower_price();
        this.upperPrice = config.upper_price();
    }

    public void validate(final Money money) {
        if (money.isLessThan(lowerPrice)) {
            throw new IllegalArgumentException(String.format("%s 는 %s 보다 작습니다.", money, lowerPrice));
        }
        if (money.isGreaterThan(upperPrice)) {
            throw new IllegalArgumentException(String.format("%s 는 %s 보다 큽니다.", money, upperPrice));
        }
    }
}
