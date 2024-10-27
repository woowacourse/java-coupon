package coupon.service;

import coupon.domain.MemberCoupon;
import coupon.domain.coupon.Coupon;
import coupon.repository.MemberCouponRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCouponService {

    private static final int MAXIMUM_OF_COUPON_COUNT = 5;
    private final MemberCouponRepository memberCouponRepository;
    private final CouponService couponService;

    public MemberCoupon issueCoupon(Long couponId, Long memberId) {
        validateCouponCanIssue(couponId);
        validateMemberCanGet(memberId, couponId);
        MemberCoupon memberCoupon = MemberCoupon.issue(couponId, memberId);
        return memberCouponRepository.save(memberCoupon);
    }

    private void validateCouponCanIssue(Long couponId) {
        Coupon coupon = couponService.getCoupon(couponId);

        if (coupon.canIssueAt(LocalDate.now())) {
            return;
        }
        throw new IllegalStateException("쿠폰 발급 가능 기간이 아닙니다.");
    }

    private void validateMemberCanGet(Long memberId, Long couponId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findByMemberIdAndCouponId(memberId, couponId);
        if (memberCoupons.size() >= MAXIMUM_OF_COUPON_COUNT) {
            throw new IllegalStateException("동일한 쿠폰은 " + MAXIMUM_OF_COUPON_COUNT + "장까지만 발급받을 수 있습니다.");
        }
    }
}
