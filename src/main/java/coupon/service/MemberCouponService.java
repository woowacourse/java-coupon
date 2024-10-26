package coupon.service;

import coupon.domain.MemberCoupon;
import coupon.domain.coupon.Coupon;
import coupon.domain.member.Member;
import coupon.repository.MemberCouponRepository;
import java.util.List;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberCouponService {

    private static final int MAX_MEMBER_COUPON_COUNT = 5;

    private final MemberCouponRepository memberCouponRepository;
    private final CouponService couponService;

    public MemberCouponService(MemberCouponRepository memberCouponRepository, CouponService couponService) {
        this.memberCouponRepository = memberCouponRepository;
        this.couponService = couponService;
    }

    @Transactional
    @CachePut(value = "member_coupon")
    public MemberCoupon issue(Member member, Coupon coupon) {
        MemberCoupon memberCoupon = new MemberCoupon(member, coupon);
        validateIssuedCount(member, coupon);
        return memberCouponRepository.save(memberCoupon);
    }

    private void validateIssuedCount(Member member, Coupon coupon) {
        List<MemberCoupon> issuedCoupons = memberCouponRepository.findAllByMemberIdAndCouponId(member.getId(), coupon.getId());
        if (issuedCoupons.size() == MAX_MEMBER_COUPON_COUNT) {
            throw new IllegalStateException("한 멤버에게 발행가능한 동일 쿠폰은 " + MAX_MEMBER_COUPON_COUNT + "개 입니다.");
        }
    }

    @Cacheable(value = "member_coupon")
    public List<Coupon> findAllMemberCoupon(Member member) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMemberId(member.getId());
        return memberCoupons.stream()
                .map(memberCoupon -> couponService.findCoupon(memberCoupon.getCouponId()))
                .toList();
    }
}
