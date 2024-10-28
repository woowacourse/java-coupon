package coupon.service;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.domain.coupon.Coupon;
import coupon.repository.CouponRepository;

@Service
@Transactional(readOnly = true)
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Transactional
    @CachePut(value = "coupon", key = "#result.id")
    public Coupon create(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Cacheable(value = "coupon", key = "#id")
    public Coupon getCoupon(long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰을 찾을 수 없습니다. id = " + id));
    }

    @Transactional
    public void deleteAll() {
        couponRepository.deleteAllInBatch();
    }
}
