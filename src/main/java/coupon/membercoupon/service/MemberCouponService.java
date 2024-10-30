package coupon.membercoupon.service;

import coupon.member.repository.MemberEntity;
import coupon.membercoupon.response.MemberCouponResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class MemberCouponService {

    private final MemberCouponReader memberCouponReader;
    private final MemberCouponWriter memberCouponWriter;

    @Transactional
    public Long issueCoupon(MemberEntity memberEntity, Long couponId) {
        return memberCouponWriter.issueCoupon(memberEntity, couponId);
    }

    @Transactional(readOnly = true)
    public List<MemberCouponResponse> findByMember(MemberEntity memberEntity) {
        return memberCouponReader.findByMember(memberEntity);
    }
}
