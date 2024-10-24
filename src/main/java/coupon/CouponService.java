package coupon;

import java.util.Optional;
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

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long id) {
        Optional<Coupon> coupon = redisCacheService.getCouponFromCache(id);

        if (coupon.isPresent()) {
            log.info("Coupon with ID {} found in cache", id);
            Coupon cacheCoupon = coupon.get();
            redisCacheService.extendCacheTTL(cacheCoupon);
            return cacheCoupon;
        }

        return getCouponFromDB(id);
    }

    private Coupon getCouponFromDB(Long id) {
        log.info("Coupon with ID {} not found in cache. Fetching from DB.", id);
        Coupon findCoupon = couponRepository.findById(id)
                .orElseGet(() -> getCouponWithNewTransaction(id));

        try {
            redisCacheService.cache(findCoupon);
        } catch (Exception e) {
            log.error("Failed to cache coupon with ID {}: {}", id, e.getMessage());
        }

        return findCoupon;
    }

    private Coupon getCouponWithNewTransaction(Long id) {
        return newTransactionExecutor.execute(() -> couponRepository.findById(id).orElseThrow());
    }

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
        redisCacheService.cache(coupon);
    }
}
