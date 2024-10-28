package coupon.memberCoupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.repository.CouponRepository;
import coupon.member.domain.Member;
import coupon.member.repository.MemberRepository;
import coupon.memberCoupon.domain.MemberCoupon;
import coupon.memberCoupon.repository.MemberCouponRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class MemberCouponService {

    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;
    private final MemberCouponRepository memberCouponRepository;

    public MemberCouponService(CouponRepository couponRepository,
                               MemberRepository memberRepository,
                               MemberCouponRepository memberCouponRepository) {
        this.couponRepository = couponRepository;
        this.memberRepository = memberRepository;
        this.memberCouponRepository = memberCouponRepository;
    }

    @Transactional
    public Long issueMemberCoupon(Long targetCouponId, Long targetMemberId) {
        Coupon targetCoupon = couponRepository.getById(targetCouponId);
        Member targetMember = memberRepository.getById(targetMemberId);

        MemberCoupon newMemberCoupon = MemberCoupon.create(targetCoupon, targetMember);
        MemberCoupon memberCoupon = memberCouponRepository.save(newMemberCoupon);

        return memberCoupon.getId();
    }
}
