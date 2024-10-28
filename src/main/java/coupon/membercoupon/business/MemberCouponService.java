package coupon.membercoupon.business;

import coupon.membercoupon.infrastructure.MemberCouponRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberCouponService {

    private MemberCouponRepository memberCouponRepository;

    @Transactional
    public void issue(Long couponId, Long memberId) {
    }
}
