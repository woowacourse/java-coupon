package coupon.service;

import coupon.config.datasource.UsingWriterSource;
import coupon.domain.Coupon;
import coupon.domain.MemberCoupon;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.service.exception.CouponBusinessLogicException;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CouponService {

    private static final long MAX_SAME_COUPON = 5;

    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;

    @Transactional(readOnly = true)
    @UsingWriterSource
    public Coupon getCoupon(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new CouponBusinessLogicException("Coupon not found ID = " + id));
    }

    @Transactional
    public void create(Coupon coupon) {
        couponRepository.save(coupon);
    }

    @Transactional
    @UsingWriterSource
    public void issueCoupon(Long memberId, Long couponId) {
        if (memberCouponRepository.countByMemberIdAndCouponId(memberId, couponId) >= MAX_SAME_COUPON) {
            throw new CouponBusinessLogicException("The coupon can no longer be issued to that user");
        }

        MemberCoupon issuedCoupon = new MemberCoupon(memberId, getCoupon(couponId), LocalDateTime.now());
        memberCouponRepository.save(issuedCoupon);
    }
}
