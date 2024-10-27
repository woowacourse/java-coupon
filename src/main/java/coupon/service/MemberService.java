package coupon.service;

import coupon.Dto.CouponOfMemberResponse;
import coupon.Dto.CouponResponse;
import coupon.domain.MemberCoupon;
import coupon.domain.coupon.Coupon;
import coupon.repository.MemberCouponRepository;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberService {

    private final MemberCouponRepository memberCouponRepository;
    private final CouponService couponService;

    public List<CouponOfMemberResponse> getCouponsOf(Long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findByMemberId(memberId);
        return memberCoupons.stream()
                .map(memberCoupon -> {
                    Long couponId = memberCoupon.getCouponId();
                    Coupon coupon = couponService.getCoupon(couponId);
                    CouponResponse couponResponse = new CouponResponse(coupon);

                    return new CouponOfMemberResponse(memberCoupon, couponResponse);
                })
                .toList();
    }
}
