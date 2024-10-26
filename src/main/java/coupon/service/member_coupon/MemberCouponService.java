package coupon.service.member_coupon;

import coupon.config.cache.RedisKey;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.repository.CouponRepository;
import coupon.domain.member.repository.MemberRepository;
import coupon.domain.member_coupon.MemberCoupon;
import coupon.domain.member_coupon.repository.MemberCouponRepository;
import coupon.exception.MemberCouponIssueLimitException;
import coupon.service.coupon.dto.CouponIssueResponse;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final RedisTemplate redisTemplate; // 제네릭을 사용해 Coupon으로 지정

    @Transactional
    public CouponIssueResponse issueMemberCoupon(long memberId, long couponId) {
        memberRepository.getById(memberId);
        Coupon coupon = getCoupon(couponId);

        MemberCoupon issuedMemberCoupon = MemberCoupon.issue(memberId, coupon);
        memberCouponRepository.save(issuedMemberCoupon);
        addMemberCoupon(memberId, coupon);

        return CouponIssueResponse.from(issuedMemberCoupon);
    }

    // TODO: 동시성 처리
    private void addMemberCoupon(long memberId, Coupon coupon) {
        String memberCouponsKey = RedisKey.MEMBER_COUPONS.getKey(memberId);
        long size = redisTemplate.opsForSet().size(memberCouponsKey);
        if(size >= 5) {
            throw new MemberCouponIssueLimitException();
        }

        String memberCouponKey = RedisKey.MEMBER_COUPON.getKey(memberId, coupon.getId());
        redisTemplate.opsForValue().set(memberCouponKey, coupon);
    }

    private Coupon getCoupon(long couponId) {
        String couponKey = RedisKey.COUPON.getKey(couponId);
        Coupon coupon = (Coupon) redisTemplate.opsForValue().get(couponKey);
        if (coupon == null) {
            coupon = couponRepository.getById(couponId);
        }
        return coupon;
    }
}
