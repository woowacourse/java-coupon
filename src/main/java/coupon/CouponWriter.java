package coupon;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Transactional
@RequiredArgsConstructor
@Component
public class CouponWriter {
    private final DiscountValidator discountValidator;
    private final CouponRepository couponRepository;
    private final DiscountLateValidator discountLateValidator;
    private final MinimumOrderPriceValidator minimumOrderPriceValidator;

    public Coupon create(final Coupon coupon) {
        discountValidator.validate(coupon.getDiscountPrice());
        discountLateValidator.validate(coupon.calculateDiscountRate());
        minimumOrderPriceValidator.validate(coupon.getMinimumOrderPrice());
        return couponRepository.save(coupon);
    }
}
