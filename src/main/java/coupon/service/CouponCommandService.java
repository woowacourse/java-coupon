package coupon.service;

import static coupon.service.CouponQueryService.CACHE_MANAGER;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.repository.MemberRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CouponCommandService {

    private static final int MAX_EQUAL_COUPON_ISSUE_COUNT = 5;

    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;
    private final MemberCouponRepository memberCouponRepository;

    public CouponCommandService(CouponRepository couponRepository, MemberRepository memberRepository,
                                MemberCouponRepository memberCouponRepository) {
        this.couponRepository = couponRepository;
        this.memberRepository = memberRepository;
        this.memberCouponRepository = memberCouponRepository;
    }

    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Cacheable(value = "coupon", key = "#id", cacheManager = CACHE_MANAGER)
    public Coupon getCoupon(Long id) {
        return couponRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));
    }

    @CacheEvict(value = "private_coupons", key = "#memberId")
    public MemberCoupon issueCoupon(Long memberId, Long couponId) {
        validateIssuedCouponCount(memberId, couponId);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버입니다."));
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));

        MemberCoupon memberCoupon = new MemberCoupon(member, coupon);

        return memberCouponRepository.save(memberCoupon);
    }

    private void validateIssuedCouponCount(Long memberId, Long couponId) {
        if (memberCouponRepository.countByMemberIdAndCouponId(memberId, couponId) >= MAX_EQUAL_COUPON_ISSUE_COUNT) {
            throw new IllegalArgumentException("동일한 쿠폰은 사용한 쿠폰 포함해서 5개까지만 발급 가능합니다.");
        }
    }
}
