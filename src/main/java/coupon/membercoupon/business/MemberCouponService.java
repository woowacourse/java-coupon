package coupon.membercoupon.business;

import coupon.coupon.domain.Coupon;
import coupon.coupon.exception.CouponErrorMessage;
import coupon.coupon.exception.CouponException;
import coupon.coupon.infrastructure.CouponRepository;
import coupon.member.exception.MemberErrorMessage;
import coupon.member.exception.MemberException;
import coupon.member.infrastructure.MemberRepository;
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
    private static final int MAXIMUM_ISSUABLE_MEMBER_COUPON = 5;

    private final MemberCouponRepository memberCouponRepository;
    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;

    public void issue(long couponId, long memberId) {
        validateIssuable(couponId, memberId);
        MemberCoupon memberCoupon = new MemberCoupon(couponId, memberId);
        memberCouponRepository.save(memberCoupon);
        MemberCouponCache.add(memberId, memberCoupon);
    }

    private void validateIssuable(long couponId, long memberId) {
        validateExistsMember(memberId);
        validateExistsCoupon(couponId);
        List<MemberCoupon> memberCoupons = memberCouponRepository.findByCouponIdAndMemberId(couponId, memberId);
        if(memberCoupons.size() >= MAXIMUM_ISSUABLE_MEMBER_COUPON) {
            throw new CouponException(CouponErrorMessage.EXCEED_MAXIMUM_ISSUABLE_COUPON);
        }
    }

    private void validateExistsMember(long memberId) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorMessage.CANNOT_FIND_MEMBER));
    }

    private void validateExistsCoupon(long couponId) {
        couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponException(CouponErrorMessage.COUPON_DOES_NOT_EXISTS_IN_MEMBER_COUPON));
    }

    public List<MemberCouponResponse> findAllMemberCoupons(long memberId) {
        validateExistsMember(memberId);
        List<MemberCoupon> memberCoupons = MemberCouponCache.get(memberId);
        if (memberCoupons == null) {
            memberCoupons = memberCouponRepository.findByMemberId(memberId);
        }
        return memberCoupons.stream()
                .map(this::generateMemberCouponResponse)
                .toList();
    }

    private MemberCouponResponse generateMemberCouponResponse(MemberCoupon memberCoupon) {
        Coupon coupon = CouponCache.get(memberCoupon.getCouponId());
        if(coupon == null) {
            coupon = couponRepository.findById(memberCoupon.getCouponId())
                    .orElseThrow(() -> new CouponException(CouponErrorMessage.COUPON_DOES_NOT_EXISTS_IN_MEMBER_COUPON));
        }
        return new MemberCouponResponse(memberCoupon, coupon);
    }
}
