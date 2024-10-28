package coupon.service.coupon;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponRepository;
import coupon.exception.CouponException;
import coupon.support.TransactionSupport;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final TransactionSupport transactionSupport;

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long id) {
        return couponRepository.findById(id)
                .orElseGet(() -> getCouponWriter(id));
    }

    private Coupon getCouponWriter(Long id) {
        return transactionSupport.executeNewTransaction(
                () -> couponRepository.findById(id).orElseThrow(() -> new CouponException("존재하지 않는 쿠폰입니다.")));
    }
}
