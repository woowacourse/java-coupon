package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.dto.MemberCouponResponse;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberCouponService {

    private static final int MAX_COUPON_COUNT = 5;

    private final MemberCouponRepository memberCouponRepository;
    private final MemberService memberService;
    private final CouponService couponService;

    public MemberCoupon issueCoupon(long memberId, long couponId) {
        Member member = memberService.read(memberId);
        validateCouponCountPerMember(member, couponId);
        return memberCouponRepository.save(new MemberCoupon(couponId, member));
    }

    public List<MemberCouponResponse> readMemberCouponsByMember(long memberId) {
        Member member = memberService.read(memberId);
        List<MemberCoupon> memberCoupons = memberCouponRepository.findByMember(member);
        return memberCoupons.stream()
                .map(memberCoupon -> MemberCouponResponse.of(memberCoupon, couponService.read(memberCoupon.getCouponId())))
                .toList();
    }

    private void validateCouponCountPerMember(Member member, long couponId) {
        int couponCountPerMember = memberCouponRepository.countByMemberAndCouponId(member, couponId);
        if (couponCountPerMember >= MAX_COUPON_COUNT) {
            throw new IllegalArgumentException("멤버 한 명당 동일한 쿠폰은 5장까지 발급 가능합니다.");
        }
    }
}
