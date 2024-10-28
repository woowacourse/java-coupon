package coupon.memberCoupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.repository.CouponRepository;
import coupon.member.domain.Member;
import coupon.member.repository.MemberRepository;
import coupon.memberCoupon.domain.MemberCoupon;
import coupon.memberCoupon.repository.MemberCouponLocalCache;
import coupon.memberCoupon.repository.MemberCouponRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class MemberCouponService {

    private static final int MAXIMUM_ISSUE_COUNT = 5;

    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final MemberCouponLocalCache memberCouponLocalCache;

    public MemberCouponService(CouponRepository couponRepository,
                               MemberRepository memberRepository,
                               MemberCouponRepository memberCouponRepository,
                               MemberCouponLocalCache memberCouponLocalCache) {
        this.couponRepository = couponRepository;
        this.memberRepository = memberRepository;
        this.memberCouponRepository = memberCouponRepository;
        this.memberCouponLocalCache = memberCouponLocalCache;
    }

    @Transactional
    public Long issueMemberCoupon(Long targetMemberId, Long targetCouponId) {
        Member targetMember = memberRepository.getById(targetMemberId);
        Coupon targetCoupon = couponRepository.getById(targetCouponId);

        validateIssueCount(targetMember, targetCoupon);

        MemberCoupon newMemberCoupon = MemberCoupon.create(targetMember, targetCoupon);
        MemberCoupon memberCoupon = memberCouponRepository.save(newMemberCoupon);
        memberCouponLocalCache.put(targetMember, targetCoupon, memberCoupon);

        return memberCoupon.getId();
    }

    private void validateIssueCount(Member targetMember, Coupon targetCoupon) {
        int memberCouponCount = memberCouponLocalCache.get(targetMember, targetCoupon).size();
        if (memberCouponCount >= MAXIMUM_ISSUE_COUNT) {
            throw new IllegalStateException(String.format("동일한 쿠폰은 최대 %d장 까지만 발급할 수 있습니다.", MAXIMUM_ISSUE_COUNT));
        }
    }
}
