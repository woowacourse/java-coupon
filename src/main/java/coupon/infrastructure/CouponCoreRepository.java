package coupon.infrastructure;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import coupon.domain.Coupon;
import coupon.domain.CouponRepository;
import lombok.RequiredArgsConstructor;

@Primary
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
        return findInCacheOrElseLoad(id);
    }

    private Coupon findInCacheOrElseLoad(long id) {
        Coupon cachedCoupon = couponCacheRepository.findById(id);
        if (cachedCoupon != null) {
            return cachedCoupon;
        }
        return loadFromDatabaseAndCache(id);
    }

    private Coupon loadFromDatabaseAndCache(long id) {
        Coupon coupon = couponJpaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("id에 해당하는 쿠폰이 존재하지 않습니다."));
        cacheCoupon(coupon);
        return coupon;
    }

    private void cacheCoupon(Coupon coupon) {
        couponCacheRepository.save(coupon);
    }
}
