package coupon.coupon.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import coupon.cache.CacheService;
import coupon.coupon.domain.Coupon;
import coupon.coupon.repository.CouponRepository;

@Service
@Transactional(readOnly = true)
public class CouponService {

    private final CouponRepository couponRepository;
    private final CacheService cacheService;

    public CouponService(CouponRepository couponRepository, CacheService cacheService) {
        this.couponRepository = couponRepository;
        this.cacheService = cacheService;
    }

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Transactional
    public Coupon getCoupon(long id) {
        return cacheService.getCoupon(id);
    }
}
