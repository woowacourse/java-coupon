package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.PublishedCoupon;
import coupon.repository.CouponCache;
import coupon.repository.CouponRepository;
import coupon.repository.MemberRepository;
import coupon.repository.PublishedCouponRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MemberCouponService {

    private static final int MAX_PUBLISHED_COUNT = 5;

    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final PublishedCouponRepository publishedCouponRepository;
    private final CouponCache couponCache;

    public MemberCouponService(MemberRepository memberRepository, CouponRepository couponRepository, PublishedCouponRepository publishedCouponRepository, CouponCache couponCache) {
        this.memberRepository = memberRepository;
        this.couponRepository = couponRepository;
        this.publishedCouponRepository = publishedCouponRepository;
        this.couponCache = couponCache;
    }

    public PublishedCoupon publishCoupon(Long memberId, Long couponId) {
        validatePublishedCount(memberId, couponId);

        Member member = getMember(memberId);
        Coupon coupon = getCouponFromCache(couponId);
        return publishedCouponRepository.save(new PublishedCoupon(member, coupon, false, LocalDateTime.now()));
    }

    private void validatePublishedCount(Long memberId, Long couponId) {
        List<PublishedCoupon> coupons = publishedCouponRepository.findAllByMemberIdAndCouponId(memberId, couponId);

        if (coupons.size() >= MAX_PUBLISHED_COUNT) {
            throw new IllegalArgumentException("Too many published coupons");
        }
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member does not exist"));
    }

    private Coupon getCouponFromCache(Long couponId) {
        return couponCache.getCoupon(couponId)
                .orElse(getCoupon(couponId));
    }

    private Coupon getCoupon(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("Coupon does not exist"));
        couponCache.putCoupon(coupon);
        return coupon;
    }
}
