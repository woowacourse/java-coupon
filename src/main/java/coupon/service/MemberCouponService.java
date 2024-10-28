package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.repository.MemberCouponRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberCouponService {

    private static final int MAX_COUPON_COUNT = 5;

    MemberCouponRepository memberCouponRepository;

    public MemberCouponService(MemberCouponRepository memberCouponRepository) {
        this.memberCouponRepository = memberCouponRepository;
    }

    public MemberCoupon issueCoupon(Coupon coupon, Member member) {
        validate(coupon, member);
        return memberCouponRepository.save(new MemberCoupon(coupon, member));
    }

    private void validate(Coupon coupon, Member member) {
        if (!coupon.canIssue()) {
            throw new IllegalArgumentException("쿠폰을 발급할 수 있는 기간이 아닙니다.");
        }

        if (memberCouponRepository.countByCouponAndMember(coupon, member) >= MAX_COUPON_COUNT) {
            throw new IllegalArgumentException(
                    String.format("멤버당 동일한 쿠폰은 최대 %d개만 발급 가능합니다.", MAX_COUPON_COUNT));
        }
    }
}
