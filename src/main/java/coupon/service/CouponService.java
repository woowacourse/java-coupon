package coupon.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.domain.Coupon;
import coupon.exception.NotFoundCouponException;
import coupon.repository.CouponRepository;
import coupon.repository.entity.CouponEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CouponService {

    public static final int MAX_READ_ATTEMPT = 2;
    private final CouponRepository couponRepository;

    @Transactional
    public CouponEntity createCoupon(final Coupon coupon) {
        return couponRepository.save(CouponEntity.toEntity(coupon));
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "coupon", key = "#id", cacheManager = "cacheManager", condition = "#id != null")
    public Coupon getCoupon(final long id) {
        final CouponEntity couponEntity = couponRepository.findById(id)
                .orElseThrow(() -> new NotFoundCouponException("쿠폰을 찾을 수 없습니다."));
        return couponEntity.toDomain();
    }
}
