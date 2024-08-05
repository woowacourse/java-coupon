package coupon.coupon.service;

import static java.util.stream.Collectors.toList;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponStatus;
import coupon.coupon.repository.CouponRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다. couponId: " + couponId));
    }

    public List<Coupon> findIssuableCoupons() {
        LocalDateTime now = LocalDateTime.now();
        return couponRepository.findAllByIssuableAndCouponStatusAndIssueStartedAtLessThanAndIssueEndedAtGreaterThan(
                        true, CouponStatus.ISSUABLE, now, now).stream()
                .filter(coupon -> coupon.isIssuableCoupon(now))
                .collect(toList());
    }
}
