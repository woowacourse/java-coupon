package coupon.application;

import coupon.domain.Coupon;
import coupon.domain.CouponRepository;
import coupon.domain.MemberCoupon;
import coupon.domain.MemberCouponRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
class CouponService {

    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;

    @Transactional
    public void issue(Long memberId, Long couponId) {
        log.info("쿠폰 발급 요청 memberId = {}, couponId = {}", memberId, couponId);
        Coupon coupon = getCoupon(couponId);
        MemberCoupon memberCoupon = coupon.issue(memberId);
        memberCouponRepository.save(memberCoupon);
    }

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Transactional
    public Coupon getCoupon(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 쿠폰입니다."));
    }
}
