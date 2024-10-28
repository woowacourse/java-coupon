package coupon.service;

import coupon.domain.coupon.Coupon;
import coupon.repository.CouponRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final TransactionHandler transactionHandler;
    private final Cache<Long, Coupon> couponCache;

    public CouponService(CouponRepository couponRepository, TransactionHandler transactionHandler,
                         Cache<Long, Coupon> couponCache) {
        this.couponRepository = couponRepository;
        this.transactionHandler = transactionHandler;
        this.couponCache = couponCache;
    }

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long id) {
        return couponCache.get(id)
                .orElseGet(() -> fetchAndCacheCoupon(id));
    }

    private Coupon fetchAndCacheCoupon(Long id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseGet(() -> getCouponOnWriter(id));
        couponCache.put(id, coupon);
        return coupon;
    }

    private Coupon getCouponOnWriter(Long id) {
        return transactionHandler.runInNewTransaction(
                TransactionDefinition.PROPAGATION_REQUIRES_NEW,
                () -> couponRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 쿠폰이 존재하지 않습니다."))
        );
    }
}
