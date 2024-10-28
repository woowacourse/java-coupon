package coupon.coupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.repository.CouponRepository;
import coupon.coupon.exception.CouponNotFoundException;
import coupon.util.TransactionExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponService {

    private final CouponRepository couponRepository;
    private final TransactionExecutor transactionExecutor;

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Cacheable(value = "coupons", key = "#couponId")
    public Coupon getCouponById(long couponId) {
        return couponRepository.findById(couponId)
                .orElseGet(() -> findCouponWithNewTransaction(couponId));
    }

    private Coupon findCouponWithNewTransaction(long couponId) {
        return transactionExecutor.executeWithNewTransaction(
                () -> couponRepository.findById(couponId)
                        .orElseThrow(() -> new CouponNotFoundException(couponId))
        );
    }
}
