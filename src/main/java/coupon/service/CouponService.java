package coupon.service;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponRepository;
import coupon.support.TransactionSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final TransactionSupport transactionSupport;
    private final CouponRepository couponRepository;

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "coupons", key = "#couponId")
    public Coupon getCoupon(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseGet(() -> getCouponWithWriter(couponId));
    }

    private Coupon getCouponWithWriter(Long couponId) {
        return transactionSupport.executeWithWriter(
                () -> couponRepository.findById(couponId)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다.")));
    }
}
