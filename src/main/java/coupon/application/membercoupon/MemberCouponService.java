package coupon.application.membercoupon;

import java.time.LocalDateTime;
import java.util.List;
import coupon.application.coupon.CouponReadService;
import coupon.application.member.MemberService;
import coupon.domain.coupon.Coupon;
import coupon.domain.member.Member;
import coupon.domain.membercoupon.MemberCoupon;
import coupon.domain.membercoupon.MemberCouponRepository;
import coupon.exception.CouponException;
import coupon.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberCouponService {

    private static final int MAX_SAME_COUPON_ISSUE_COUNT = 5;

    private final MemberCouponRepository memberCouponRepository;
    private final CouponReadService couponReadService;
    private final MemberService memberService;

    public List<MemberCouponResponse> getMemberCoupons(Long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMemberId(memberId);

        return memberCoupons.stream()
                .map(this::getMemberCouponResponse)
                .toList();
    }

    public MemberCouponResponse issueMemberCoupon(Long memberId, Long couponId) {
        validateNotOverMaxIssueCount(memberId, couponId);
        Member member = memberService.getMember(memberId);
        MemberCoupon memberCoupon = new MemberCoupon(couponId, member, LocalDateTime.now());
        return getMemberCouponResponse(memberCoupon);
    }

    private void validateNotOverMaxIssueCount(Long memberId, Long couponId) {
        Long count = memberCouponRepository.countByMemberIdAndCouponId(memberId, couponId);
        if (count >= MAX_SAME_COUPON_ISSUE_COUNT) {
            throw new CouponException(ExceptionType.ISSUE_MAX_COUNT_EXCEED);
        }
    }

    private MemberCouponResponse getMemberCouponResponse(MemberCoupon memberCoupon) {
        Coupon coupon = couponReadService.getCoupon(memberCoupon.getCouponId());
        return MemberCouponResponse.of(memberCoupon, coupon);
    }
}
