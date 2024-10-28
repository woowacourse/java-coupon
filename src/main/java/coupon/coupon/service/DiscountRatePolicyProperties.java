package coupon.coupon.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "coupon.policy.rate")
public record DiscountRatePolicyProperties(int minRate, int maxRate) {
}
