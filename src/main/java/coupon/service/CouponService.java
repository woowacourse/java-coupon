package coupon.service;

import coupon.config.ReadWithoutLag;
import coupon.domain.Coupon;
import coupon.repository.CouponRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Coupon with id %d does not exists", id)));
    }

    @ReadWithoutLag
    @Transactional(readOnly = true)
    public Coupon getCouponWithoutLag(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Coupon with id %d does not exists", id)));
    }
}
