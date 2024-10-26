package coupon.coupon.persistence;

import coupon.coupon.domain.MemberCoupon;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberCouponReader {

    private final MemberCouponRepository memberCouponRepository;

    public List<MemberCoupon> findAllByMemberId(long memberId) {
        return memberCouponRepository.findAllByMemberId(memberId);
    }

    public int countByMemberIdAndCouponId(long memberId, long couponId) {
        return memberCouponRepository.countByMemberIdAndCouponId(memberId, couponId);
    }
}
