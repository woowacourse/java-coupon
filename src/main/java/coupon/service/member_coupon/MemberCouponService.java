package coupon.service.member_coupon;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.repository.CouponCacheRepository;
import coupon.domain.coupon.repository.CouponRepository;
import coupon.domain.member.repository.MemberRepository;
import coupon.domain.member_coupon.MemberCoupon;
import coupon.domain.member_coupon.repository.MemberCouponRepository;
import coupon.service.coupon.dto.CouponIssueResponse;
import coupon.service.member_coupon.dto.MemberCouponsResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCouponService {


    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final CouponCacheRepository couponCacheRepository;

    @Transactional(readOnly = true)
    public MemberCouponsResponse getMemberCoupons(long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findByMemberId(memberId);
        return MemberCouponsResponse.from(memberCoupons);
    }

    @Transactional
    public CouponIssueResponse issueMemberCoupon(long memberId, long couponId) {
        memberRepository.getById(memberId);

        MemberCoupon issuedMemberCoupon = MemberCoupon.issue(memberId, getCoupon(couponId));
        memberCouponRepository.save(issuedMemberCoupon);
        couponCacheRepository.updateIssuedMemberCouponCount(memberId, couponId);

        return CouponIssueResponse.from(issuedMemberCoupon);
    }



    private Coupon getCoupon(long couponId) {
        Coupon coupon = couponCacheRepository.getCoupon(couponId);
        if(coupon == null) {
            coupon = couponRepository.getById(couponId);
        }

        return coupon;
    }
}
