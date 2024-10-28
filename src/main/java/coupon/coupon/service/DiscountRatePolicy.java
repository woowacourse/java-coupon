package coupon.coupon.service;

import coupon.coupon.domain.DiscountPolicy;
import coupon.coupon.domain.DiscountPolicyViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(DiscountRatePolicyProperties.class)
@RequiredArgsConstructor
public class DiscountRatePolicy implements DiscountPolicy {

    private final DiscountRatePolicyProperties properties;

    @Override
    public void validatePolicy(long fixedDiscountAmount, long minimumOrderPrice) {
        long discountRate = getFlooredDiscountRate(fixedDiscountAmount, minimumOrderPrice);
        if (discountRate < properties.minRate() || discountRate > properties.maxRate()) {
            throw new DiscountPolicyViolationException(
                    "할인율은 " + properties.minRate() + " 이상 " + properties.maxRate() + " 이하여야 합니다."
            );
        }
    }

    private long getFlooredDiscountRate(long fixedDiscountAmount, long minimumOrderPrice) {
        return (fixedDiscountAmount * 100) / minimumOrderPrice;
    }
}
