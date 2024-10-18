package coupon;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final TransactionSupport transactionSupport;

    @Transactional
    public Coupon create(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long id) {
        return couponRepository.findById(id)
                .orElseGet(() -> getCouponWithWriter(id));
    }

    private Coupon getCouponWithWriter(Long id) {
        return transactionSupport.executeNewTransaction(() -> couponRepository.findById(id))
                .orElseThrow(() -> new IllegalArgumentException("id가 " + id + "인 쿠폰이 존재하지 않습니다."));
    }
}
