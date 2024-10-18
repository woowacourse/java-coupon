package coupon.coupon.business;

import coupon.coupon.domain.Coupon;
import coupon.coupon.persistence.CouponReader;
import coupon.coupon.persistence.CouponWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponWriter couponWriter;
    private final CouponReader couponReader;
    private final TransactionRouter transactionRouter;

    public void create(Coupon coupon) {
        couponWriter.create(coupon);
    }

    public Coupon getCoupon(long couponId) {
        try {
            return couponReader.getCoupon(couponId);
        } catch (IllegalArgumentException exception) {
            return transactionRouter.route(() -> couponReader.getCoupon(couponId));
        }
    }
}
