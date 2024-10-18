package coupon.service;

import coupon.domain.coupon.Coupon;
import coupon.exception.CouponException;
import coupon.repository.CouponRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final RoutingMasterTemplate routingMasterTemplate;

    public CouponService(CouponRepository couponRepository, RoutingMasterTemplate routingMasterTemplate) {
        this.couponRepository = couponRepository;
        this.routingMasterTemplate = routingMasterTemplate;
    }

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long couponId) {
        return findCoupon(couponId).orElseGet(() -> routingMasterTemplate.apply(
                () -> findCoupon(couponId).orElseThrow(() -> new CouponException("존재하지 않는 쿠폰입니다."))));
    }

    private Optional<Coupon> findCoupon(Long couponId) {
        return couponRepository.findById(couponId);
    }
}
