package coupon.service.coupon;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponRepository;
import coupon.domain.coupon.MemberCoupon;
import coupon.domain.coupon.MemberCouponRepository;
import coupon.exception.CouponException;
import coupon.support.TransactionSupport;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CouponService {

    private static final int MAX_ISSUE_COUNT = 5;

    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;
    private final TransactionSupport transactionSupport;

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long id) {
        return couponRepository.findById(id)
                .orElseGet(() -> getCouponWriter(id));
    }

    private Coupon getCouponWriter(Long id) {
        return transactionSupport.executeNewTransaction(
                () -> couponRepository.findById(id).orElseThrow(() -> new CouponException("존재하지 않는 쿠폰입니다.")));
    }

    @Transactional
    public MemberCoupon issue(Long memberId, Long couponId) {
        LocalDateTime issuedAt = LocalDateTime.now();
        Coupon coupon = getCoupon(couponId);

        validateIssueCount(memberId, couponId);

        MemberCoupon memberCoupon = new MemberCoupon(memberId, coupon, issuedAt);
        return memberCouponRepository.save(memberCoupon);
    }

    private void validateIssueCount(Long memberId, Long couponId) {
        int issueCount = memberCouponRepository.countByMemberIdAndCouponId(memberId, couponId);
        if (issueCount > MAX_ISSUE_COUNT) {
            throw new CouponException("동일한 쿠폰은 최대 %d장까지 발급할 수 있습니다.".formatted(MAX_ISSUE_COUNT));
        }
    }
}
