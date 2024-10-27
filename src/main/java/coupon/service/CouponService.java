package coupon.service;

import coupon.entity.Coupon;
import coupon.exception.CouponNotFoundException;
import coupon.helper.TransactionExecutor;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final TransactionExecutor transactionExecutor;

    @Transactional
    public Long create(Coupon coupon) {
        Coupon savedCoupon = couponRepository.save(coupon);
        return savedCoupon.getId();
    }

    @Transactional(readOnly = true)
    public Coupon getCouponWithReplicaLag(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new CouponNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Coupon getCouponWithoutReplicaLag(Long id) {
        return couponRepository.findById(id)
                .orElseGet(() -> getCouponFromWriterDatabase(id));
    }

    public Coupon getCouponFromWriterDatabase(Long id) {
        return transactionExecutor.executeNewTransaction(() ->
                couponRepository.findById(id)
                        .orElseThrow(() -> new CouponNotFoundException(id))
        );
    }
}
