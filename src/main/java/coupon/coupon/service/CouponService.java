package coupon.coupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponRepository;
import coupon.coupon.dto.CouponCreateRequest;
import coupon.coupon.exception.CouponApplicationException;
import coupon.member.domain.Member;
import coupon.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Cacheable(value = "coupons", key = "#couponId")
    public Coupon getCouponByAdmin(final Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponApplicationException("쿠폰이 존재하지 않습니다."));
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "coupons", key = "#couponId")
    public Coupon getCoupon(final Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponApplicationException("쿠폰이 존재하지 않습니다."));
    }

    @Transactional
    public Coupon createCoupon(final CouponCreateRequest couponRequest) {
        final var issuer = findIssuer(couponRequest.issuerId());
        return couponRepository.save(couponRequest.toCouponEntity(issuer));
    }

    private Member findIssuer(final Long issuerId) {
        return memberRepository.findById(issuerId)
                .orElseThrow(() -> new CouponApplicationException("쿠폰 발급자 정보를 찾을 수 없습니다"));
    }

    @Transactional
    @CacheEvict(value = "coupons", allEntries = true)
    public void deleteAll() {
        couponRepository.deleteAll();
    }
}
