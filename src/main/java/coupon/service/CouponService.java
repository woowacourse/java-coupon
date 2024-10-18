package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.repository.CouponRepository;
import coupon.util.TransactionExecutor;
import org.springframework.stereotype.Service;

@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final TransactionExecutor transactionExecutor;

    public CouponService(CouponRepository couponRepository, TransactionExecutor transactionExecutor) {
        this.couponRepository = couponRepository;
        this.transactionExecutor = transactionExecutor;
    }

    public Coupon create(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    public Coupon getById(Long id) {
        return couponRepository.findById(id)
                .orElseGet(() -> getCouponWithWriter(id));
    }

    private Coupon getCouponWithWriter(Long id) {
        return transactionExecutor.executeWithWriter(
                () -> couponRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Coupon with id " + id + " not found"))
        );
    }
}
