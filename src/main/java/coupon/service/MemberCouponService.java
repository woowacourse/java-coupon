package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.CouponRepository;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.domain.MemberCouponRepository;
import coupon.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private static final int MAX_ISSUE_MEMBER_COUPON_COUNT = 5;

    private final MemberCouponRepository memberCouponRepository;
    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public long create(long memberId, long couponId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("쿠폰이 유효하지 않습니다."));

        validateMemberCouponIssueLimit(member, coupon);

        MemberCoupon memberCoupon = new MemberCoupon(member, coupon);
        memberCouponRepository.save(memberCoupon);
        return memberCoupon.getId();
    }

    private void validateMemberCouponIssueLimit(Member member, Coupon coupon) {
        int couponCount = memberCouponRepository.findAllByMemberAndCoupon(member, coupon).size();
        if (couponCount >= MAX_ISSUE_MEMBER_COUPON_COUNT) {
            throw new IllegalArgumentException(
                    "발급 가능한 쿠폰 최대 개수는 " + MAX_ISSUE_MEMBER_COUPON_COUNT + "개입니다. 현재 개수: " + couponCount
            );
        }
    }
}
