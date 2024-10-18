package coupon.service;


import coupon.domain.Coupon;
import coupon.entity.CouponEntity;
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

    public CouponEntity create(CouponEntity coupon) {
        return couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(long id) {
        CouponEntity couponEntity = couponRepository.findById(id)
                .orElse(getCouponRetry(id));

        return couponEntity.toCoupon();
    }

    private CouponEntity getCouponRetry(long id) {
        return transactionSupporter.executeNewTransaction(() -> couponRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new));
    }
}
