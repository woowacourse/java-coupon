package coupon.service;

import coupon.domain.MemberCoupon;
import coupon.repository.MemberCouponRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;

    public MemberCouponService(MemberCouponRepository memberCouponRepository) {
        this.memberCouponRepository = memberCouponRepository;
    }

    @Transactional(readOnly = true)
    public List<MemberCoupon> getMemberCoupon(long memberId) {
        return memberCouponRepository.findAllByMemberId(memberId);
    }
}
