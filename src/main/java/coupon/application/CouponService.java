package coupon.application;

import coupon.domain.Coupon;
import coupon.domain.CouponRepository;
import coupon.dto.CouponResponse;
import coupon.aop.redissonLock.DistributedLock;
import coupon.aop.replication.ReplicationLag;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final RedissonClient redisson;
    private final CouponRepository couponRepository;

    @Transactional
    public Long create(Coupon coupon) {
        Coupon savedCoupon = couponRepository.save(coupon);
        return savedCoupon.getId();
    }

    @ReplicationLag
    @Cacheable(value = "coupons", key = "#couponId")
    @Transactional(readOnly = true)
    public CouponResponse findCoupon(Long couponId) {
        Coupon coupon = getCoupon(couponId);

        return CouponResponse.from(coupon);
    }

    @DistributedLock(value = "couponLock", key = "couponId")
    @Transactional
    public void updateDiscountAmount(Long couponId, int discountAmount) {
        Coupon coupon = getCoupon(couponId);
        coupon.changeDiscountAmount(discountAmount);
    }

    @DistributedLock(value = "couponLock", key = "couponId")
    @Transactional
    public void updateMinOrderAmount(Long couponId, int minOrderAmount) {
        Coupon coupon = getCoupon(couponId);
        coupon.changeMinOrderAmount(minOrderAmount);
    }

    private Coupon getCoupon(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰이 존재하지 않습니다."));
    }
}
