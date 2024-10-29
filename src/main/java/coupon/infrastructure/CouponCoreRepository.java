package coupon.infrastructure;

import org.springframework.stereotype.Repository;

import coupon.domain.Coupon;
import coupon.domain.CouponRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CouponCoreRepository implements CouponRepository {

    private final CouponJpaRepository couponJpaRepository;
    private final CouponCacheRepository couponCacheRepository;

    @Override
    public Coupon save(Coupon coupon) {
        return couponJpaRepository.save(coupon);
    }

    @Override
    public Coupon findById(long id) {
        Coupon cachedCoupon = couponCacheRepository.getCachedCoupon(id);
        if (cachedCoupon != null) {
            return cachedCoupon;
        }

        Coupon coupon = couponJpaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("id에 해당하는 쿠폰이 존재하지 않습니다."));
        couponCacheRepository.save(coupon);
        return coupon;
    }
}
