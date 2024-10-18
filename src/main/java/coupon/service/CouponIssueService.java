package coupon.service;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.IssuedCoupon;
import coupon.repository.CouponRepository;
import coupon.repository.IssuedCouponEntity;
import coupon.repository.IssuedCouponRepository;
import coupon.repository.MemberRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponIssueService {

    private final CouponRepository couponRepository;
    private final IssuedCouponRepository issuedCouponRepository;
    private final MemberRepository memberRepository;

    public IssuedCouponEntity issueCoupon(long memberId, long couponId) {
        Coupon coupon = couponRepository.findByIdOrThrow(couponId)
                .toDomain();
        memberRepository.findByIdOrThrow(memberId);

        IssuedCoupon issuedCoupon = new IssuedCoupon(coupon, LocalDateTime.now());
        return issuedCouponRepository.save(IssuedCouponEntity.from(memberId, issuedCoupon));
    }
}
