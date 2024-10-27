package coupon.service;

import coupon.domain.membercoupon.MemberCoupon;
import coupon.repository.MemberCouponRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private static final int ISSUANCE_LIMIT = 5;

    private final MemberCouponRepository memberCouponRepository;

    @Transactional
    public void save(long couponId, long memberId) {
        MemberCoupon memberCoupon = new MemberCoupon(couponId, memberId);
        validateIssuanceLimit(couponId, memberId);
        memberCouponRepository.save(memberCoupon);
    }

    private void validateIssuanceLimit(long couponId, long memberId) {
        int issuanceCount = memberCouponRepository.countByCouponIdAndMemberId(couponId, memberId);
        if (issuanceCount >= ISSUANCE_LIMIT) {
            throw new IllegalArgumentException("회원은 동일한 쿠폰을 최대 " + ISSUANCE_LIMIT + "장까지 발급할 수 있습니다.");
        }
    }

    @Transactional(readOnly = true)
    public MemberCoupon findById(long couponId) {
        return memberCouponRepository.findById(couponId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원 쿠폰입니다."));
    }
}
