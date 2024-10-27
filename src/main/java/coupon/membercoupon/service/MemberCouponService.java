package coupon.membercoupon.service;

import java.util.List;
import org.springframework.stereotype.Service;
import coupon.CouponException;
import coupon.coupon.domain.Coupon;
import coupon.member.domain.Member;
import coupon.membercoupon.domain.MemberCoupon;
import coupon.membercoupon.repository.MemberCouponRepository;

@Service
public class MemberCouponService {

    private static final int MAX_MEMBER_COUPON_COUNT = 5;
    private static final String MAX_MEMBER_COUPON_COUNT_MESSAGE = String.format(
            "동일한 쿠폰을 %d장 이상 발급할 수 없어요.",
            MAX_MEMBER_COUPON_COUNT
    );
    private final MemberCouponRepository memberCouponRepository;

    public MemberCouponService(MemberCouponRepository memberCouponRepository) {
        this.memberCouponRepository = memberCouponRepository;
    }

    public void issue(Member member, Coupon coupon) {
        List<MemberCoupon> memberCoupons = getMemberCoupons(member, coupon);
        validate(memberCoupons);
        MemberCoupon memberCoupon = MemberCoupon.issue(member, coupon);
        memberCouponRepository.save(memberCoupon);
    }

    private List<MemberCoupon> getMemberCoupons(Member member, Coupon coupon) {
        return memberCouponRepository.findAllByMemberIdAndCouponId(member.getId(), coupon.getId()).stream().toList();
    }

    private void validate(List<MemberCoupon> memberCoupons) {
        if (memberCoupons.size() >= MAX_MEMBER_COUPON_COUNT) {
            throw new CouponException(MAX_MEMBER_COUPON_COUNT_MESSAGE);
        }
    }
}
