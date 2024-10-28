package coupon;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final RedisCacheService redisCacheService;
    private final NewTransactionExecutor<Coupon> newTransactionExecutor;
    private final LockService lockService;

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long id) {
        return redisCacheService.getCouponFromCache(id)
                .map(this::extendTTLAndGetCoupon)
                .orElseGet(() -> getCouponFromDB(id));
    }

    private Coupon extendTTLAndGetCoupon(Coupon coupon) {
        log.info("Coupon with ID {} found in cache", coupon.getId());
        lockService.executeWithLock(coupon.getId(), () -> redisCacheService.extendCacheTTL(coupon));
        return coupon;
    }

    private Coupon getCouponFromDB(Long id) {
        log.info("Coupon with ID {} not found in cache. Fetching from DB.", id);
        Coupon findCoupon = couponRepository.findById(id)
                .orElseGet(() -> getCouponWithNewTransaction(id));
        executeWithLock(findCoupon, () -> redisCacheService.cache(findCoupon));
        return findCoupon;
    }

    private void executeWithLock(Coupon findCoupon, Runnable runnable) {
        try {
            lockService.executeWithLock(findCoupon.getId(), runnable);
        } catch (Exception e) {
            log.error("Failed to cache coupon with ID {}: {}", findCoupon.getId(), e.getMessage());
        }
    }

    private Coupon getCouponWithNewTransaction(Long id) {
        return newTransactionExecutor.execute(() -> couponRepository.findById(id).orElseThrow());
    }

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
        lockService.executeWithLock(coupon.getId(), () -> redisCacheService.cache(coupon));
    }
}
