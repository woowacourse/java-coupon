package coupon.service.db.writer;

import coupon.domain.coupon.Coupon;
import coupon.repository.CouponRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CouponWriter {

    private final CouponRepository couponRepository;

    public CouponWriter(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public Coupon save(Coupon coupon){
        return couponRepository.save(coupon);
    }
}
