package coupon.application.membercoupon;

import java.util.List;
import coupon.domain.MemberCoupon;
import coupon.domain.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;
    private final MemberCouponMapper memberCouponMapper;

    public List<MemberCouponResponse> getMemberCoupons(Long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMemberId(memberId);

        return memberCouponMapper.map(memberCoupons);
    }
}
