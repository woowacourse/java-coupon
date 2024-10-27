package coupon.service;

import coupon.domain.membercoupon.MemberCoupon;
import coupon.repository.MemberCouponRepository;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberCouponService {

    private MemberCouponRepository memberCouponRepository;

    @Transactional
    public void save(long couponId, long memberId) {
        MemberCoupon memberCoupon = new MemberCoupon(couponId, memberId);
        memberCouponRepository.save(memberCoupon);
    }

    @Transactional(readOnly = true)
    public MemberCoupon findById(long couponId) {
        return memberCouponRepository.findById(couponId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원 쿠폰입니다."));
    }
}
