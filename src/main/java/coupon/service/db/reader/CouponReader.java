package coupon.service.db.reader;

import coupon.domain.coupon.Coupon;
import coupon.repository.CouponRepository;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class CouponReader {

    private final CouponRepository couponRepository;

    public CouponReader(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public Optional<Coupon> findCoupon(long couponId) {
        return couponRepository.findById(couponId);
    }
}
