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

    public CouponService(CouponRepository couponRepository, TransactionHandler transactionHandler) {
        this.couponRepository = couponRepository;
        this.transactionHandler = transactionHandler;
    }

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long id) {
        return couponRepository.findById(id)
                .orElseGet(() -> getCouponOnWriter(id));
    }

    private Coupon getCouponOnWriter(Long id) {
        return transactionHandler.runInNewTransaction(
                TransactionDefinition.PROPAGATION_REQUIRES_NEW,
                () -> couponRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 쿠폰이 존재하지 않습니다."))
        );
    }
}
