package coupon.service;

import coupon.domain.coupon.Coupon;
import coupon.domain.membercoupon.MemberCoupon;
import coupon.dto.response.FindMemberCouponResponse;
import coupon.repository.MemberCouponRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private static final String MEMBER_COUPON_COUNT_CACHE_FORMAT = "member-coupons-count::%d::%d";
    private static final int CACHE_EXPIRATION_MINUTES = 10;
    private static final int ISSUANCE_LIMIT = 5;

    private final RedisTemplate<String, Integer> redisTemplate;
    private final ReadCouponService readCouponService;
    private final MemberCouponRepository memberCouponRepository;


    @Transactional
    public void save(long couponId, long memberId) {
        validateIssuanceLimit(couponId, memberId);
        validateIssuancePeriod(couponId);

        MemberCoupon memberCoupon = new MemberCoupon(couponId, memberId);
        memberCouponRepository.save(memberCoupon);
    }

    private void validateIssuancePeriod(long couponId) {
        Coupon coupon = readCouponService.findById(couponId);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startAt = coupon.getIssuancePeriod().getStartAt();
        LocalDateTime endAt = coupon.getIssuancePeriod().getEndAt();

        if (now.isBefore(startAt) || now.isAfter(endAt)) {
            throw new IllegalArgumentException("해당 쿠폰은 " + startAt + "부터 " + endAt + "까지 발급할 수 있습니다.");
        }
    }

    private void validateIssuanceLimit(long couponId, long memberId) {
        String key = MEMBER_COUPON_COUNT_CACHE_FORMAT.formatted(couponId, memberId);
        Integer issuanceCount = redisTemplate.opsForValue().get(key);
        if (issuanceCount == null) {
            issuanceCount = memberCouponRepository.countByCouponIdAndMemberId(couponId, memberId);
            redisTemplate.opsForValue().set(key, issuanceCount, CACHE_EXPIRATION_MINUTES, TimeUnit.MINUTES);
        }

        Long updatedIssuanceCount = redisTemplate.opsForValue().increment(key);
        if (updatedIssuanceCount == null) {
            throw new IllegalStateException("회원 쿠폰 발급을 위한 Redis 연산 중 문제가 발생했습니다.");
        }
        if (updatedIssuanceCount > ISSUANCE_LIMIT) {
            redisTemplate.opsForValue().decrement(key);
            throw new IllegalArgumentException("회원은 동일한 쿠폰을 최대 " + ISSUANCE_LIMIT + "장까지 발급할 수 있습니다.");
        }
    }

    @Transactional(readOnly = true)
    public List<FindMemberCouponResponse> findByMemberId(long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMemberId(memberId);
        return memberCoupons.stream()
                .map(this::findCoupon)
                .toList();
    }

    private FindMemberCouponResponse findCoupon(MemberCoupon memberCoupon) {
        Coupon coupon = readCouponService.findById(memberCoupon.getCouponId());
        return FindMemberCouponResponse.of(memberCoupon, coupon);
    }
}
