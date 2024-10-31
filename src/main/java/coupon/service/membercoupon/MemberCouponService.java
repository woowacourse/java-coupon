package coupon.service.membercoupon;

import coupon.domain.coupon.Coupon;
import coupon.domain.member.Member;
import coupon.domain.membercoupon.MemberCoupon;
import coupon.domain.membercoupon.MemberCouponRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private final String CACHE_NAME = "memberCouponCache";
    private final MemberCouponRepository memberCouponRepository;

    @Transactional
    @CachePut(value = CACHE_NAME, key = "#result.id")
    public MemberCoupon create(Member member, Coupon coupon) {
        MemberCoupon memberCoupon = new MemberCoupon(member, coupon);
        return memberCouponRepository.save(memberCoupon);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = CACHE_NAME, key = "#root.args[0]", unless = "#root.args[0] == null")
    public List<MemberCoupon> getMemberCoupons(Long memberId) {
        return memberCouponRepository.findAllByMemberId(memberId);
    }
}
