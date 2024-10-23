package coupon.service;

import coupon.domain.Coupon;
import coupon.exception.NotFoundException;
import coupon.repository.CouponRepository;
import coupon.util.TransactionExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final TransactionExecutor transactionExecutor;

    public CouponService(CouponRepository couponRepository, TransactionExecutor transactionExecutor) {
        this.couponRepository = couponRepository;
        this.transactionExecutor = transactionExecutor;
    }

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }

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
