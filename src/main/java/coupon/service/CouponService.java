package coupon.service;

import coupon.domain.Coupon;
import coupon.exception.NotFoundException;
import coupon.repository.CouponRepository;
import coupon.util.TransactionExecutor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CouponService {

    private static final String CACHE_KEY_COUPON = "coupons";

    private final CouponRepository couponRepository;
    private final TransactionExecutor transactionExecutor;

    public CouponService(CouponRepository couponRepository, TransactionExecutor transactionExecutor) {
        this.couponRepository = couponRepository;
        this.transactionExecutor = transactionExecutor;
    }

    @CachePut(value = CACHE_KEY_COUPON, key = "#coupon.id")
    @Transactional
    public Coupon create(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Cacheable(value = CACHE_KEY_COUPON, key = "#id")
    @Transactional(readOnly = true)
    public Coupon getCoupon(long id) {
        return couponRepository.findById(id)
                .orElseGet(() -> findCouponWithNonReadonlyTransaction(id));
    }

    private Coupon findCouponWithNonReadonlyTransaction(long id) {
        return transactionExecutor.executeOnWriter(
                () -> couponRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("쿠폰이 존재하지 않습니다. id: " + id))
        );
    }
}
