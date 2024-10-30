package coupon.membercoupon.application;

import java.util.concurrent.TimeUnit;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponRepository;
import coupon.member.domain.Member;
import coupon.member.domain.MemberRepository;
import coupon.membercoupon.domain.MemberCoupon;
import coupon.membercoupon.domain.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCouponIssuer {

    private static final String MEMBER_COUPON_COUNT_CACHE_FORMAT = "member-coupons-count::%d::%d";
    private static final int MAX_MEMBER_COUPON_COUNT = 5;

    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final RedisTemplate<String, Integer> redisTemplate;

    @Transactional
    public Long issue(Long memberId, Long couponId) {
        Member member = getMember(memberId);
        Coupon coupon = getCoupon(couponId);

        increaseMemberCouponCount(member.getId(), couponId);

        MemberCoupon memberCoupon = memberCouponRepository.save(MemberCoupon.issue(member.getId(), coupon));

        return memberCoupon.getId();
    }

    private void increaseMemberCouponCount(Long memberId, Long couponId) {
        String key = MEMBER_COUPON_COUNT_CACHE_FORMAT.formatted(memberId, couponId);

        int memberCouponCount = getMemberCouponCount(memberId, couponId);
        if (memberCouponCount + 1 > MAX_MEMBER_COUPON_COUNT) {
            throw new IllegalArgumentException("회원 쿠폰 발급 한도 초과 %d/%d".formatted(memberCouponCount, MAX_MEMBER_COUPON_COUNT));
        }

        redisTemplate.opsForValue().increment(key);
    }

    private int getMemberCouponCount(Long memberId, Long couponId) {
        String key = MEMBER_COUPON_COUNT_CACHE_FORMAT.formatted(memberId, couponId);

        Integer memberCountFromCache = redisTemplate.opsForValue().get(key);
        if (memberCountFromCache != null) {
            return memberCountFromCache;
        }

        int countFromDB = (int) memberCouponRepository.countByMemberIdAndCouponId(memberId, couponId);
        redisTemplate.opsForValue().set(key, countFromDB, 30, TimeUnit.MINUTES);
        return countFromDB;
    }

    private Coupon getCoupon(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("Coupon not found: " + couponId));
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + memberId));
    }
}
