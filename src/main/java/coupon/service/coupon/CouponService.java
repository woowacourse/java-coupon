package coupon.service.coupon;

import coupon.entity.coupon.Coupon;
import coupon.exception.coupon.CouponNotFoundException;
import coupon.helper.TransactionExecutor;
import coupon.repository.coupon.CouponRepository;
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

    private Coupon getCouponFromWriterDatabase(Long id) {
        return transactionExecutor.executeNewTransaction(() ->
                couponRepository.findById(id)
                        .orElseThrow(() -> new CouponNotFoundException(id))
        );
    }
}
