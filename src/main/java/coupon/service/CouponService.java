package coupon.service;


import coupon.entity.Coupon;
import coupon.repository.CouponRepository;
import coupon.support.TransactionSupporter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final TransactionSupporter transactionSupporter;

    public Coupon create(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(long id) {
        return couponRepository.findById(id)
                .orElse(getCouponRetry(id));
    }

    private Coupon getCouponRetry(long id) {
        return transactionSupporter.executeNewTransaction(() -> couponRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new));
    }
}
