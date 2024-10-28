package coupon.membercoupon.business;

import coupon.coupon.exception.CouponErrorMessage;
import coupon.coupon.exception.CouponException;
import coupon.coupon.infrastructure.CouponRepository;
import coupon.membercoupon.domain.MemberCoupon;
import coupon.membercoupon.dto.MemberCouponResponse;
import coupon.membercoupon.infrastructure.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;
    private final CouponRepository couponRepository;

    public void issue(long couponId, long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findByCouponIdAndMemberId(couponId, memberId);
        validateIssuableCoupon(memberCoupons);
        memberCouponRepository.save(new MemberCoupon(couponId, memberId));
    }

    private void validateIssuableCoupon(List<MemberCoupon> memberCoupons) {
        if (memberCoupons.size() >= 5) {
            throw new CouponException(CouponErrorMessage.EXCEED_MAXIMUM_ISSUABLE_COUPON);
        }
    }

    public List<MemberCouponResponse> findAllMemberCoupons(long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findByMemberId(memberId);
        return memberCoupons.stream()
                .map(memberCoupon -> new MemberCouponResponse(
                        memberCoupon,
                        couponRepository.findById(memberCoupon.getCouponId())
                                .orElseThrow(() -> new CouponException(CouponErrorMessage.COUPON_DOES_NOT_EXISTS_IN_MEMBER_COUPON))
                ))
                .toList();
    }
}
