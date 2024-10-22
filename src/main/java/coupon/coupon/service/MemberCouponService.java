package coupon.coupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.MemberCoupon;
import coupon.coupon.domain.repository.CouponRepository;
import coupon.coupon.domain.repository.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private static final int MAX_COUPON_COUNT_PER_MEMBER = 5;

    private final MemberCouponRepository memberCouponRepository;
    private final CouponRepository couponRepository;

    @Transactional
    public MemberCoupon issueCoupon(Long memberId, Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("해당 쿠폰이 존재하지 않습니다. couponId:%d".formatted(couponId)));

        validateMemberCouponLimit(memberId, coupon);
        MemberCoupon memberCoupon = new MemberCoupon(coupon, memberId);
        return memberCouponRepository.save(memberCoupon);
    }

    private void validateMemberCouponLimit(Long memberId, Coupon coupon) {
        int issuedCouponCount = memberCouponRepository.countByMemberIdAndCoupon(memberId, coupon);
        if (issuedCouponCount > MAX_COUPON_COUNT_PER_MEMBER) {
            throw new IllegalStateException("1인당 동일한 쿠폰은 5장까지 발급할 수 있습니다. 현재 발급 횟수:%d".formatted(issuedCouponCount));
        }
    }
}
