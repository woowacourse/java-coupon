package coupon.service;

import coupon.domain.coupon.Coupon;
import coupon.domain.member.Member;
import coupon.domain.member.MemberRepository;
import coupon.domain.membercoupon.MemberCoupon;
import coupon.domain.membercoupon.MemberCouponRepository;
import coupon.service.dto.MemberCouponResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private static final int MAX_MEMBER_COUPON_COUNT = 5;

    private final CouponService couponService;

    private final MemberRepository memberRepository;

    private final MemberCouponRepository memberCouponRepository;

    @Transactional
    public void issue(Long memberId, Long couponId) {
        Member member = getMember(memberId);
        Coupon coupon = couponService.getCoupon(couponId);
        validateMemberCouponCount(memberId, couponId);

        MemberCoupon memberCoupon = MemberCoupon.issue(LocalDateTime.now(), member, coupon);
        memberCouponRepository.save(memberCoupon);
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버입니다."));
    }

    private void validateMemberCouponCount(Long memberId, Long couponId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findByMemberIdAndCouponId(memberId, couponId);
        if (memberCoupons.size() >= MAX_MEMBER_COUPON_COUNT) {
            throw new IllegalArgumentException("이미 %d장의 쿠폰을 발급받았습니다.".formatted(MAX_MEMBER_COUPON_COUNT));
        }
    }

    @Transactional(readOnly = true)
    public List<MemberCouponResponse> findMemberCoupons(Long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findByMemberId(memberId);
        return memberCoupons.stream()
                .map(this::toMemberCouponResponse)
                .toList();
    }

    private MemberCouponResponse toMemberCouponResponse(MemberCoupon memberCoupon) {
        Coupon coupon = couponService.getCoupon(memberCoupon.getCouponId());
        return MemberCouponResponse.of(memberCoupon, coupon);
    }
}
