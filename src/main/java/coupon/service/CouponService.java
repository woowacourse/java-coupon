package coupon.service;

import coupon.domain.Coupon;
import coupon.entity.CouponEntity;
import coupon.repository.CouponRepository;
import coupon.service.support.CacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final CacheService cacheService;

    @Transactional
    public Coupon create(Coupon coupon) {
        CouponEntity couponEntity = new CouponEntity(coupon);
        return couponRepository.save(couponEntity).toDomain();
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long id) {
        return cacheService.getCoupon(id);
    }
}
