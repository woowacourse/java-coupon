package coupon.coupon.service;

import coupon.coupon.controller.response.CouponResponse;
import coupon.coupon.controller.response.CouponsResponse;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.MemberCoupon;
import coupon.coupon.domain.repository.CouponRepository;
import coupon.coupon.domain.repository.MemberCouponRepository;
import coupon.coupon.exception.CouponNotFoundException;
import coupon.member.domain.Member;
import coupon.member.domain.repository.MemberRepository;
import coupon.member.exception.MemberNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberCouponService {

    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void issueCoupon(long memberId, long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException(couponId));

        Member member = getMember(memberId);

        coupon.issue();
        memberCouponRepository.save(new MemberCoupon(member, coupon));
    }

    public CouponsResponse getCoupons(long memberId) {
        Member member = getMember(memberId);

        List<CouponResponse> coupons = memberCouponRepository.findAllByMember(member)
                .stream()
                .map(memberCoupon -> toCouponResponse(memberCoupon.getCoupon()))
                .toList();

        return new CouponsResponse(coupons);
    }

    private Member getMember(long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    private CouponResponse toCouponResponse(Coupon coupon) {
        return new CouponResponse(coupon.getId(), coupon.getName(), coupon.getDiscountAmount(),
                coupon.getMinimumOrderPrice(), coupon.getCouponCategory().getDescription(), coupon.getIssueEndedAt());
    }
}
