package coupon.coupon.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "coupon.policy.fixed")
public record DiscountAmountPolicyProperties(int minAmount, int maxAmount, int unit) {
}
