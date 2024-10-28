package coupon.membercoupon.application;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import coupon.coupon.application.CouponService;
import coupon.coupon.domain.Coupon;
import coupon.membercoupon.domain.MemberCoupon;
import coupon.membercoupon.domain.MemberCouponRepository;
import coupon.membercoupon.dto.FindAllMemberCouponResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private static final int MAX_COUPON_COUNT = 5;

    private final MemberCouponRepository memberCouponRepository;
    private final CouponService couponService;

    public void createMemberCoupon(MemberCoupon memberCoupon) {
        validateIssuedCouponCount(memberCoupon.getMemberId());
        memberCouponRepository.save(memberCoupon);
    }

    private void validateIssuedCouponCount(Long memberId) {
        if (memberCouponRepository.countByMemberId(memberId) >= MAX_COUPON_COUNT) {
            throw new IllegalArgumentException("발급 가능한 최대 쿠폰 개수를 초과하였습니다. memberId: " + memberId);
        }
    }

    public FindAllMemberCouponResponse getMemberCouponsByMemberId(Long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findByMemberId(memberId);
        Map<MemberCoupon, Coupon> memberCouponWithCoupons = memberCoupons.stream()
                .collect(Collectors.toMap(mc -> mc, mc -> couponService.getCoupon(mc.getCouponId())));

        return FindAllMemberCouponResponse.of(memberCouponWithCoupons);
    }
}
