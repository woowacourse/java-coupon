package coupon.membercoupon.business;

import coupon.coupon.domain.Coupon;
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
        MemberCoupon memberCoupon = new MemberCoupon(couponId, memberId);
        memberCouponRepository.save(memberCoupon);
        MemberCouponCache.add(memberId, memberCoupon);
    }

    public List<MemberCouponResponse> findAllMemberCoupons(long memberId) {
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
