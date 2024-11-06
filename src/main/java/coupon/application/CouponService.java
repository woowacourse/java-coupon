package coupon.application;

import coupon.domain.Coupon;
import coupon.domain.CouponRepository;
import coupon.dto.CouponResponse;
import coupon.replication.ReplicationLag;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final RedissonClient redisson;
    private final CouponRepository couponRepository;
    private final CouponSynchronizedService couponSynchronizedService;


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


    //    @Transactional
    public void updateDiscountAmount(Long couponId, int discountAmount) {
        RLock lock = redisson.getLock(String.format("coupon:couponId:%d", couponId));
        try {
            boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS.SECONDS);
            if (!available) {
                System.out.println("redisson lock timeout");
                throw new IllegalArgumentException();
            }
            couponSynchronizedService.updateDiscountAmount(couponId, discountAmount);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    //    @Transactional
    public void updateMinOrderAmount(Long couponId, int minOrderAmount) {
        RLock lock = redisson.getLock(String.format("coupon:couponId:%d", couponId));
        try {
            boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS.SECONDS);
            if (!available) {
                System.out.println("redisson lock timeout");
                throw new IllegalArgumentException();
            }
            couponSynchronizedService.updateMinOrderAmount(couponId, minOrderAmount);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    private Coupon getCoupon(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰이 존재하지 않습니다."));
    }
}
