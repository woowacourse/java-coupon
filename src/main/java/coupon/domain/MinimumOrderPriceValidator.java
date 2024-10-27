package coupon.domain;

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
            throw new IllegalArgumentException(String.format("최소 주문 금액이 %s 이상 이어야 한다. ( 현재 금액 : %s )", lowerPrice, money));
        }
        if (money.isGreaterThan(upperPrice)) {
            throw new IllegalArgumentException(String.format("최대 주문 금액이 %s 이하여야 한다. ( 현재 금액 : %s )", upperPrice, money));
        }
    }
}
