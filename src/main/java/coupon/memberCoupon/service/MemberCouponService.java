package coupon.memberCoupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.repository.CouponRepository;
import coupon.member.domain.Member;
import coupon.member.repository.MemberRepository;
import coupon.memberCoupon.cache.MemberCouponLocalCache;
import coupon.memberCoupon.domain.MemberCoupon;
import coupon.memberCoupon.dto.MemberCouponResponse;
import coupon.memberCoupon.repository.MemberCouponRepository;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberCouponService {

    private static final int MAXIMUM_ISSUE_COUNT = 5;

    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final MemberCouponLocalCache memberCouponLocalCache;

    public MemberCouponService(MemberRepository memberRepository,
                               CouponRepository couponRepository,
                               MemberCouponRepository memberCouponRepository,
                               MemberCouponLocalCache memberCouponLocalCache) {
        this.memberRepository = memberRepository;
        this.couponRepository = couponRepository;
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
        memberCouponLocalCache.put(targetMember.getId(), targetCoupon.getId(), memberCoupon);

        return memberCoupon.getId();
    }

    private void validateIssueCount(Member targetMember, Coupon targetCoupon) {
        int memberCouponCount = memberCouponLocalCache.get(targetMember.getId(), targetCoupon.getId()).size();
        if (memberCouponCount >= MAXIMUM_ISSUE_COUNT) {
            throw new IllegalStateException(String.format("동일한 쿠폰은 최대 %d장 까지만 발급할 수 있습니다.", MAXIMUM_ISSUE_COUNT));
        }
    }

    @Transactional(readOnly = true)
    public Set<MemberCouponResponse> findAllMemberCouponsByMemberId(Long targetMemberId) {
        Member targetMember = memberRepository.getById(targetMemberId);
        return memberCouponLocalCache.getAllByMemberId(targetMember.getId());
    }
}
