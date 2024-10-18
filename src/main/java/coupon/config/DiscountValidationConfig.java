package coupon.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import java.math.BigDecimal;

@PropertySource(value = "classpath:validation.yml") // 외부 YAML 파일 경로 지정
@ConfigurationProperties(prefix = "discount")
public record DiscountValidationConfig(
        BigDecimal discountMinPrice,
        BigDecimal discountMaxPrice,
        BigDecimal discountUnit
) {
}
