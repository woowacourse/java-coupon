package coupon.coupon.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import coupon.CouponException;
import coupon.coupon.domain.Coupon;
import coupon.coupon.repository.CouponRepository;

@Service
@Transactional(readOnly = true)
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Transactional
    public Coupon getCouponByAdmin(long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new CouponException("요청하신 쿠폰을 찾을 수 없어요."));
    }
}
