package coupon.membercoupon.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.coupon.service.CouponService;
import coupon.member.service.MemberService;
import coupon.membercoupon.controller.dto.MemberCouponResponses;
import coupon.membercoupon.domain.MemberCoupon;
import coupon.membercoupon.domain.MemberCoupons;
import coupon.membercoupon.respository.MemberCouponRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberCouponService {
    private static final String CACHE_NAMES = "member_coupons";

    private final MemberCouponRepository memberCouponRepository;
    private final MemberService memberService;
    private final CouponService couponService;

    @Transactional
    public MemberCoupon create(Long memberId, Long couponId) {
        validateMemberExist(memberId);
        validateCouponExist(couponId);
        validateMaxFiveCouponsPerMember(memberId, couponId);
        MemberCoupon memberCoupon = new MemberCoupon(memberId, couponId);

        return memberCouponRepository.save(memberCoupon);
    }

    private void validateMemberExist(Long memberId) {
        memberService.readByIdFromReaderWithCache(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 멤버가 없습니다."));
    }

    private void validateCouponExist(Long couponId) {
        couponService.readByIdFromReaderWithCache(couponId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 쿠폰이 없습니다."));
    }

    private void validateMaxFiveCouponsPerMember(Long memberId, Long couponId) {
        MemberCoupons memberCoupons = new MemberCoupons(memberCouponRepository.findAllByMemberIdAndCouponId(memberId, couponId));
        if (memberCoupons.hasSizeFiveOrMore()) {
            throw new IllegalArgumentException("한 명의 회원이 동일한 쿠폰을 사용한 쿠폰을 포함하여 5장 초과로 발급할 수 없습니다.");
        }
    }

    @Transactional
    @Cacheable(value = CACHE_NAMES, key = "#memberId")
    public List<MemberCoupon> readAllByMemberId(Long memberId) {
        return memberCouponRepository.findAllByMemberId(memberId);
    }
}
