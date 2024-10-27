package coupon.member.service;

import java.util.List;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.coupon.domain.entity.CouponEntity;
import coupon.member.domain.Member;
import coupon.member.domain.MemberCoupon;
import coupon.member.repository.MemberCouponRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;

    @Transactional
    @CachePut(value = "memberCouponCache", key = "#result.id")
    public MemberCoupon create(Member member, CouponEntity couponEntity) {
        MemberCoupon memberCoupon = new MemberCoupon(member, couponEntity);
        return memberCouponRepository.save(memberCoupon);
    }


    @Transactional(readOnly = true)
    @Cacheable(value = "memberCouponCache", key = "#root.args[0]", unless = "#root.args[0] == null")
    public List<MemberCoupon> getMemberCoupons(Long memberId) {
        return memberCouponRepository.findAllByMemberId(memberId);
    }
}
