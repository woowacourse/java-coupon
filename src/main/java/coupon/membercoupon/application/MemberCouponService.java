package coupon.membercoupon.application;

import java.util.List;
import coupon.coupon.application.CouponResponse;
import coupon.coupon.application.CouponService;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponRepository;
import coupon.member.domain.Member;
import coupon.member.domain.MemberRepository;
import coupon.membercoupon.domain.MemberCoupon;
import coupon.membercoupon.domain.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final CouponService couponService;
    private final MemberCouponRepository memberCouponRepository;
    private final MemberCouponMapper memberCouponMapper;

    @Transactional(readOnly = true)
    public List<MemberCouponWithCouponResponse> getMemberCouponWithCoupons(Long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findByMemberId(memberId);

        return memberCoupons.stream()
                .map(memberCoupon -> {
                    CouponResponse couponResponse = couponService.getCoupon(memberCoupon.getCouponId());
                    System.out.println("couponResponse: " + couponResponse);
                    return memberCouponMapper.toWithCouponResponse(memberCoupon, couponResponse);
                })
                .toList();
    }

    @Transactional
    public void issueMemberCoupon(Long memberId, Long couponId) {
        Member member = getMember(memberId);
        Coupon coupon = getCoupon(couponId);

        memberCouponRepository.save(MemberCoupon.issue(memberId, coupon));
    }

    private Coupon getCoupon(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("Coupon not found: " + couponId));
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + memberId));
    }
}
