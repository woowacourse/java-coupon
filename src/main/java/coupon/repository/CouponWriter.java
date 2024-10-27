package coupon.repository;

import coupon.domain.Coupon;
import coupon.domain.DiscountLateValidator;
import coupon.domain.DiscountValidator;
import coupon.domain.MinimumOrderPriceValidator;
import coupon.util.SessionUtil;
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
    private final SessionUtil sessionUtil;

    public Coupon create(final Coupon coupon) {
        sessionUtil.logSessionStatus("create");
        discountValidator.validate(coupon.getDiscountPrice());
        discountLateValidator.validate(coupon.calculateDiscountRate());
        minimumOrderPriceValidator.validate(coupon.getMinimumOrderPrice());
        return couponRepository.save(coupon);
    }
}
