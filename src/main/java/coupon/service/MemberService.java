package coupon.service;

import coupon.aspect.ImmediateRead;
import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.dto.MemberCouponResponse;
import coupon.exception.CouponException;
import coupon.repository.MemberRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final CouponService couponService;
    private final MemberRepository memberRepository;

    public MemberService(CouponService couponService, MemberRepository memberRepository) {
        this.couponService = couponService;
        this.memberRepository = memberRepository;
    }

    @ImmediateRead
    @Transactional(readOnly = true)
    public List<MemberCouponResponse> getCoupons(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CouponException("해당 사용자가 존재하지 않습니다."));

        return member.getMemberCoupons().stream()
                .map(memberCoupon -> MemberCouponResponse.of(memberCoupon,
                        couponService.getCoupon(memberCoupon.getCouponId())))
                .toList();

    }

    @Transactional
    public MemberCoupon issueCoupon(Long memberId, Long couponId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CouponException("해당 사용자가 존재하지 않습니다."));
        Coupon coupon = couponService.getCoupon(couponId);

        validateMaxCouponCount(member, coupon);

        MemberCoupon memberCoupon = member.addCoupon(coupon);

        return memberCoupon;
    }

    private void validateMaxCouponCount(Member member, Coupon coupon) {
        Long couponId = coupon.getId();

        long couponCount = member.getMemberCoupons().stream()
                .filter(memberCoupon -> couponId.equals(memberCoupon.getCouponId()))
                .count();

        if (couponCount >= 5) {
            throw new CouponException("같은 쿠폰은 최대 5장까지 발급 가능합니다.");
        }
    }
}
