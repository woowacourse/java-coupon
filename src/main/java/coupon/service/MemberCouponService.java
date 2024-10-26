package coupon.service;

import coupon.domain.MemberCoupon;
import coupon.domain.coupon.Coupon;
import coupon.domain.member.Member;
import coupon.repository.MemberCouponRepository;
import java.util.List;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

public class MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;

    public MemberCouponService(MemberCouponRepository memberCouponRepository) {
        this.memberCouponRepository = memberCouponRepository;
    }

    @Transactional
    @CachePut(value = "member_coupon")
    public MemberCoupon issue(Member member, Coupon coupon) {
        MemberCoupon memberCoupon = new MemberCoupon(member, coupon);
        return memberCouponRepository.save(memberCoupon);
    }

    @Cacheable(value = "member_coupon")
    public List<MemberCoupon> getMemberCoupon(Member member) {
        return memberCouponRepository.findAllByMemberId(member.getId());
    }


}
