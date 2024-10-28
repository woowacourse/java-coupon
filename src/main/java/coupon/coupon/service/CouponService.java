package coupon.coupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.repository.CouponRepository;
import coupon.infrastructure.WriterDatabase;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @WriterDatabase
    @CachePut(value = "coupon", key = "#result.id", condition = "#result != null")
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Transactional
    @Cacheable(value = "coupon", key = "#couponId")
    public Coupon getCoupon(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다. 쿠폰 ID : " + couponId));
    }
}
