package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.repository.CouponRepository;
import coupon.util.TransactionExecutor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CouponService {

    private final CouponRepository couponRepository;
    private final TransactionExecutor transactionExecutor;

    public CouponService(CouponRepository couponRepository, TransactionExecutor transactionExecutor) {
        this.couponRepository = couponRepository;
        this.transactionExecutor = transactionExecutor;
    }

    @Transactional
    public Coupon create(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Cacheable(key = "#id", value = "coupon")
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
