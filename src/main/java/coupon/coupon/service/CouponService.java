package coupon.coupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.MemberCoupon;
import coupon.coupon.repository.CouponRepository;
import coupon.coupon.repository.MemberCouponRepository;
import coupon.datasource.aop.WriteTransaction;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;

    @WriteTransaction
    @Transactional
    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    public Coupon readCoupon(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 쿠폰입니다."));
    }

    @WriteTransaction
    @Transactional
    public MemberCoupon issueCoupon(MemberCoupon memberCoupon) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByCouponAndMember(
                memberCoupon.getCoupon(),
                memberCoupon.getMember()
        );

        validateIssuedMemberCouponCount(memberCoupons);

        return memberCouponRepository.save(memberCoupon);
    }

    private void validateIssuedMemberCouponCount(List<MemberCoupon> memberCoupons) {
        if (memberCoupons.size() >= 5) {
            throw new IllegalArgumentException("이미 5개 이상 발급된 쿠폰입니다.");
        }
    }
}
