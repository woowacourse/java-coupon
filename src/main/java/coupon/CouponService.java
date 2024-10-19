package coupon;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;

    private final Map<Long, Coupon> cache = new ConcurrentHashMap<>();

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long id) {
        log.info("CouponService.getCoupon Transaction readOnly {}", TransactionSynchronizationManager.isCurrentTransactionReadOnly());
        if (cache.containsKey(id)) {
            return cache.get(id);
        }
        return couponRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void create(Coupon coupon) {
        log.info("CouponService.create Transaction readOnly {}", TransactionSynchronizationManager.isCurrentTransactionReadOnly());
        couponRepository.save(coupon);
        cache.put(coupon.getId(), coupon);
    }
}
