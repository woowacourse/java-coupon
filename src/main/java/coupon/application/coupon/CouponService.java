package coupon.application.coupon;

import coupon.domain.Coupon;
import coupon.domain.CouponRepository;
import coupon.domain.MemberCoupon;
import coupon.domain.MemberCouponRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponService {

    private static final int COUPON_ISSUE_LIMIT = 5;

    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;

    @Transactional
    public void issue(Long memberId, Long couponId) {
        log.info("쿠폰 발급 요청 memberId = {}, couponId = {}", memberId, couponId);
        validateIssuableLimit(memberId, couponId);
        Coupon coupon = getCoupon(couponId);
        MemberCoupon memberCoupon = coupon.issue(memberId);
        memberCouponRepository.save(memberCoupon);
    }

    private void validateIssuableLimit(Long memberId, Long couponId) {
        long couponIssueCount = memberCouponRepository.countMemberCouponByMemberIdAndCouponId(memberId, couponId);

        if (couponIssueCount >= COUPON_ISSUE_LIMIT) {
            throw new IllegalStateException("더 이상 해당 쿠폰을 발급할 수 없습니다.");
        }
    }

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Transactional
    public Coupon getCoupon(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 쿠폰입니다."));
    }

    @Cacheable(key = "#couponId", value = "coupon", cacheManager = "couponCacheManager")
    @Transactional(readOnly = true)
    public Coupon getReadCoupon(Long couponId) {
        log.info("쿠폰 캐시 미스 발생 couponId = {}", couponId);

        return couponRepository.findById(couponId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 쿠폰입니다."));
    }
}
