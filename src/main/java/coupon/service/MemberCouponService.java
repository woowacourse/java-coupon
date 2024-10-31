package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.repository.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private static final int COUPON_ISSUE_LIMIT = 5;
    private final MemberCouponRepository memberCouponRepository;

    @Transactional
    public MemberCoupon issue(Member member, Coupon coupon) {
        validate(member, coupon);
        MemberCoupon issued = MemberCoupon.issue(member, coupon);
        return memberCouponRepository.save(issued);
    }

    private void validate(Member member, Coupon coupon) {
        if (memberCouponRepository.countByMemberAndCouponId(member, coupon.getId()) > COUPON_ISSUE_LIMIT) {
            throw new IllegalArgumentException("발급 수량 제한을 초과하였습니다.");
        }
    }

    @Transactional(readOnly = true)
    public List<MemberCoupon> readAllMemberCoupons(Member member) {
        return memberCouponRepository.findByMember(member);
    }
}
