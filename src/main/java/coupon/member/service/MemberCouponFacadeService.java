package coupon.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.coupon.domain.entity.CouponEntity;
import coupon.coupon.service.CouponService;
import coupon.member.domain.Member;
import coupon.member.domain.MemberCoupon;
import coupon.member.request.MemberCouponRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberCouponFacadeService {

    private final MemberCouponService memberCouponService;
    private final MemberService memberService;
    private final CouponService couponService;

    @Transactional
    public MemberCoupon create(MemberCouponRequest memberCouponRequest) {

        Member member = memberService.getMember(memberCouponRequest.memberId());
        CouponEntity couponEntity = couponService.getCoupon(memberCouponRequest.couponId());

        return memberCouponService.create(member, couponEntity);
    }
}
