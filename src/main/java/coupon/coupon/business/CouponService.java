package coupon.coupon.business;

import coupon.coupon.domain.Coupon;
import coupon.coupon.persistence.CouponReader;
import coupon.coupon.persistence.CouponWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
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
            log.error("(읽기 DB 복제 지연 오류 발생 : {}) 쓰기 DB 조회", exception.getMessage());
            return transactionRouter.route(() -> couponReader.getCoupon(couponId));
        }
    }
}
