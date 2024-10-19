package coupon.coupon.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponRepository;
import coupon.infrastructure.datasource.TransactionRouter;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final TransactionRouter transactionRouter;

    public void create(final Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(final Long id) {
        return couponRepository.findById(id)
                .orElseGet(() -> getCouponFromWrite(id));
    }

    public Coupon getCouponFromWrite(final Long id) {
        return transactionRouter.routeWrite(() -> couponRepository.fetchById(id));
    }
}
