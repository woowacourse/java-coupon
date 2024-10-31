package coupon.coupon.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "coupon")
public record CouponIssuePolicyProperties(int issueLimit) {
}
