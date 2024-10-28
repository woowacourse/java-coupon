package coupon.service;

import coupon.repository.MemberCouponRepository;
import coupon.repository.entity.Coupon;
import coupon.repository.entity.Member;
import coupon.repository.entity.MemberCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;

    @Transactional
    public void issueCoupon(Member member, Coupon coupon) {
        validateCount(member, coupon);
        MemberCoupon memberCoupon = new MemberCoupon(member, coupon);
        memberCouponRepository.save(memberCoupon);
    }

    private void validateCount(Member member, Coupon coupon) {
        if (memberCouponRepository.countByMemberAndCoupon(member, coupon) >= 5) {
            throw new IllegalArgumentException("한 명의 회원은 동일한 쿠폰을 최대 5장까지 발급할 수 있습니다.");
        }
    }
}
