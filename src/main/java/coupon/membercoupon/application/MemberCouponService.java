package coupon.membercoupon.application;

import java.util.List;
import java.util.Map;
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

    @Transactional
    public void issueMemberCoupon(Long memberId, Long couponId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + memberId));
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("Coupon not found: " + couponId));

        memberCouponRepository.save(MemberCoupon.issue(memberId, coupon));
    }

    @Transactional(readOnly = true)
    public List<MemberCouponWithCouponResponse> getMemberCouponWithCoupons(Long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findByMemberId(memberId);
        List<Long> couponIds = getCouponIds(memberCoupons);
        Map<Long, CouponResponse> couponMap = couponService.getCouponResponseMap(couponIds);

        return memberCouponMapper.toWithCouponResponses(memberCoupons, couponMap);
    }

    private List<Long> getCouponIds(List<MemberCoupon> memberCoupons) {
        return memberCoupons.stream()
                .map(MemberCoupon::getCouponId)
                .distinct()
                .toList();
    }
}
