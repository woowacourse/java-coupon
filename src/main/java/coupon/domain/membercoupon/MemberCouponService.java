package coupon.domain.membercoupon;

import coupon.domain.coupon.Coupon;
import coupon.domain.member.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;

    @Transactional
    @CachePut(value = "memberCouponCache", key = "#result.id")
    public MemberCoupon create(Member member, Coupon coupon) {
        MemberCoupon memberCoupon = new MemberCoupon(member, coupon);
        return memberCouponRepository.save(memberCoupon);
    }


    @Transactional(readOnly = true)
    @Cacheable(value = "memberCouponCache", key = "#root.args[0]", unless = "#root.args[0] == null")
    public List<MemberCoupon> getMemberCoupons(Long memberId) {
        return memberCouponRepository.findAllByMemberId(memberId);
    }
}
