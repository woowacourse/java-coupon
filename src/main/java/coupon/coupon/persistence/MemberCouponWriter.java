package coupon.coupon.persistence;

import coupon.coupon.domain.MemberCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class MemberCouponWriter {


    private final MemberCouponRepository memberCouponRepository;

    public MemberCoupon create(MemberCoupon memberCoupon) {
        return memberCouponRepository.save(memberCoupon);
    }
}
