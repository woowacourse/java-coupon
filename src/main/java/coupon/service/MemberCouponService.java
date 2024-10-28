package coupon.service;


import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponRepository;
import coupon.domain.coupon.MemberCoupon;
import coupon.domain.coupon.MemberCouponRepository;
import coupon.domain.member.Member;
import coupon.domain.member.MemberRepository;

import coupon.service.dto.MemberCouponResponse;
import coupon.support.TransactionSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private static final int SAME_COUPON_LIMIT = 5;

    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final MemberRepository memberRepository;
    private final TransactionSupport transactionSupport;

    @Transactional
    public MemberCoupon create(long couponId, long memberId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다. couponId: " + couponId));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. memberId: " + memberId));
        validateSameCouponCount(couponId, member, coupon);

        MemberCoupon memberCoupon = new MemberCoupon(member, coupon);
        return memberCouponRepository.save(memberCoupon);
    }

    private void validateSameCouponCount(long couponId, Member member, Coupon coupon) {
        List<MemberCoupon> totalCoupons = memberCouponRepository.findAllByMemberAndCoupon(member, coupon);
        if (totalCoupons.size() >= SAME_COUPON_LIMIT) {
            throw new IllegalArgumentException("이미 %d개 이상의 동일 쿠폰을 보유하고 있습니다. couponId: %d".formatted(SAME_COUPON_LIMIT, couponId));
        }
    }

    @Transactional(readOnly = true)
    public List<MemberCouponResponse> getMemberCoupons(Member member) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMember(member);
        if (memberCoupons.isEmpty()) {
            return transactionSupport.withNewTransaction(() -> getMemberCouponsFromWriteDB(member));
        }
        return memberCoupons.stream()
                .map(this::convertToMemberCouponResponse)
                .toList();
    }

    public List<MemberCouponResponse> getMemberCouponsFromWriteDB(Member member) {
        return memberCouponRepository.findAllByMember(member)
                .stream()
                .map(this::convertToMemberCouponResponse)
                .toList();
    }

    private MemberCouponResponse convertToMemberCouponResponse(MemberCoupon memberCoupon) {
        long couponId = memberCoupon.getCoupon().getId();
        Coupon coupon = couponRepository.findById(couponId).orElseThrow();
        return MemberCouponResponse.of(memberCoupon, coupon);
    }
}
