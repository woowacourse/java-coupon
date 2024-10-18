package coupon.api.service;

import coupon.api.repository.CouponRepository;
import coupon.common.exception.CouponNotFoundException;
import coupon.common.manager.TransactionManager;
import coupon.domain.coupon.CouponDomain;
import coupon.entity.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public void create(CouponDomain coupon) {
        couponRepository.save(new Coupon(coupon));
    }

    @Transactional(readOnly = true)
    public Coupon searchCoupon(Long couponId) {
        return couponRepository.findCouponById(couponId)
                .orElse(searchFromReader(couponId));
    }

    private Coupon searchFromReader(Long couponId) {
        return new TransactionManager().newTransaction(
                () -> couponRepository.findCouponById(couponId)
                        .orElseThrow(CouponNotFoundException::new)
        );
    }
}
