package coupon.member.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.coupon.domain.entity.CouponEntity;
import coupon.coupon.service.CouponService;
import coupon.member.domain.Member;
import coupon.member.domain.MemberCoupon;
import coupon.member.request.MemberCouponRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberCouponFacadeService {

    private final MemberCouponService memberCouponService;
    private final MemberService memberService;
    private final CouponService couponService;

    @Transactional
    @CacheEvict(value = "memberCouponCache", key = "#root.args[0].memberId")
    public MemberCoupon create(MemberCouponRequest request) {
        Member member = memberService.getMember(request.memberId());
        CouponEntity couponEntity = couponService.getCouponById(request.couponId());
        return memberCouponService.create(member, couponEntity);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "memberCouponCache", key = "#root.args[0]", unless = "#root.args[0] == null")
    public List<CouponEntity> getAllCouponByMemberId(Long memberId) {
        List<Long> couponIds = memberCouponService.getCouponIdsByMemberId(memberId);
        return couponService.getAllInCouponId(couponIds);
    }
}
