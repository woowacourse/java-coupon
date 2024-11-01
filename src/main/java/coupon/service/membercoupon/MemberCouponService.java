package coupon.service.membercoupon;

import coupon.domain.coupon.Coupon;
import coupon.domain.member.Member;
import coupon.domain.membercoupon.MemberCoupon;
import coupon.domain.membercoupon.MemberCouponRepository;
import coupon.exception.CouponException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private static final int ISSUED_COUNT = 5;
    private static final String CACHE_NAME = "memberCouponCache";
    private final MemberCouponRepository memberCouponRepository;

    @Transactional
    @CachePut(value = CACHE_NAME, key = "#result.id")
    public MemberCoupon create(Member member, Coupon coupon) {
        validateIssuedCount(member, coupon);
        MemberCoupon memberCoupon = new MemberCoupon(member, coupon);
        return memberCouponRepository.save(memberCoupon);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = CACHE_NAME, key = "#root.args[0]", unless = "#root.args[0] == null")
    public List<MemberCoupon> getMemberCoupons(Long memberId) {
        return memberCouponRepository.findAllByMemberId(memberId);
    }

    private void validateIssuedCount(Member member, Coupon coupon) {
        if (memberCouponRepository.countByMemberAndCoupon(member, coupon) >= ISSUED_COUNT) {
            throw new CouponException("최대 %s장까지 발급할 수 있습니다.".formatted(ISSUED_COUNT));
        }
    }
}
