package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.exception.ErrorMessage;
import coupon.exception.GlobalCustomException;
import coupon.repository.MemberCouponRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCouponService {
    private static final int ISSUE_LIMIT = 5;

    private final MemberCouponRepository memberCouponRepository;

    @Transactional
    public MemberCoupon issue(Member member, Coupon coupon) {
        List<MemberCoupon> issuedMemberCoupons = memberCouponRepository.findByMemberAndCoupon(member, coupon);
        if (issuedMemberCoupons.size() >= ISSUE_LIMIT) {
            throw new GlobalCustomException(ErrorMessage.EXCEED_ISSUE_MEMBER_COUPON);
        }
        MemberCoupon memberCoupon = new MemberCoupon(coupon, member, false);
        return memberCouponRepository.save(memberCoupon);
    }
}
