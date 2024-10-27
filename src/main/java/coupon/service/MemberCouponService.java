package coupon.service;

import coupon.domain.MemberCoupon;
import coupon.domain.coupon.Coupon;
import coupon.repository.MemberCouponRepository;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;
    private final CouponService couponService;

    public MemberCoupon issueCoupon(Long couponId, Long memberId) {
        validateCouponCanIssue(couponId);
        MemberCoupon memberCoupon = MemberCoupon.issue(couponId, memberId);
        memberCouponRepository.save(memberCoupon);
        return memberCoupon;
    }

    private void validateCouponCanIssue(Long couponId) {
        Coupon coupon = couponService.getCoupon(couponId);

        if (coupon.canIssueAt(LocalDate.now())) {
            return;
        }
        throw new IllegalStateException("쿠폰 발급 가능 기간이 아닙니다.");
    }
}
