package coupon.service;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponRepository;
import coupon.support.TransactionSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final TransactionSupport transactionSupport;

    @Transactional
    public long create(Coupon coupon) {
        Coupon saved = couponRepository.save(coupon);
        return saved.getId();
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(long couponId) {
        return couponRepository.findById(couponId)
                .orElseGet(() -> findFromWriterDB(couponId));
    }

    private Coupon findFromWriterDB(long couponId) {
        return transactionSupport.withNewTransaction(
                () -> couponRepository.findById(couponId)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다. couponId: " + couponId))
        );
    }
}
