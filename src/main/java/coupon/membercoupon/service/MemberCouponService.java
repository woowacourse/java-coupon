package coupon.membercoupon.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import coupon.CouponException;
import coupon.cache.CacheService;
import coupon.coupon.domain.Coupon;
import coupon.member.domain.Member;
import coupon.membercoupon.domain.MemberCoupon;
import coupon.membercoupon.repository.MemberCouponRepository;

@Service
@Transactional(readOnly = true)
public class MemberCouponService {

    private static final int MAX_MEMBER_COUPON_COUNT = 5;
    private static final String MAX_MEMBER_COUPON_COUNT_MESSAGE = String.format(
            "동일한 쿠폰을 %d장 이상 발급할 수 없어요.",
            MAX_MEMBER_COUPON_COUNT
    );

    private final MemberCouponRepository memberCouponRepository;
    private final CacheService cacheService;

    public MemberCouponService(MemberCouponRepository memberCouponRepository, CacheService cacheService) {
        this.memberCouponRepository = memberCouponRepository;
        this.cacheService = cacheService;
    }

    @Transactional
    public void issue(Member member, Coupon coupon) {
        List<MemberCoupon> memberCoupons = getMemberCoupons(member, coupon);
        validate(memberCoupons);
        MemberCoupon memberCoupon = MemberCoupon.issue(member, coupon);
        memberCouponRepository.save(memberCoupon);
    }

    private List<MemberCoupon> getMemberCoupons(Member member, Coupon coupon) {
        return memberCouponRepository.findAllByMemberIdAndCouponId(member.getId(), coupon.getId()).stream().toList();
    }

    private void validate(List<MemberCoupon> memberCoupons) {
        if (memberCoupons.size() >= MAX_MEMBER_COUPON_COUNT) {
            throw new CouponException(MAX_MEMBER_COUPON_COUNT_MESSAGE);
        }
    }

    public List<Coupon> findAllCouponByMember(Member member) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMemberId(member.getId());
        return memberCoupons.stream()
                .map(cacheService::getCoupon)
                .toList();
    }
}
