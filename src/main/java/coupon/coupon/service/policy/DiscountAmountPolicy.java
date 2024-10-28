package coupon.coupon.service.policy;

import coupon.coupon.domain.DiscountPolicy;
import coupon.coupon.domain.DiscountPolicyViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(DiscountAmountPolicyProperties.class)
@RequiredArgsConstructor
public class DiscountAmountPolicy implements DiscountPolicy {

    private final DiscountAmountPolicyProperties properties;

    @Override
    public void validatePolicy(long discountAmount, long minimumOrderPrice) {
        if (discountAmount < properties.minAmount() || discountAmount > properties.maxAmount()) {
            throw new DiscountPolicyViolationException(
                    "할인 금액은 " + properties.minAmount() + "원 이상 " + properties.maxAmount() + "원 이하여야 합니다."
            );
        }
        if (discountAmount % properties.unit() != 0) {
            throw new DiscountPolicyViolationException("할인 금액은 " + properties.unit() + "원 단위로 입력되어야 합니다.");
        }
    }
}
