package coupon.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = "classpath:validation.yml") // 외부 YAML 파일 경로 지정
@ConfigurationProperties(prefix = "discount-rate")
public record DiscountLateValidationConfig (
        double minRate,
        double maxRate
){
}
