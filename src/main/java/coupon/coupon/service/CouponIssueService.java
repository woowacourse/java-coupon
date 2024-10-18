package coupon.coupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.IssuedCoupon;
import coupon.coupon.repository.CouponRepository;
import coupon.coupon.repository.IssuedCouponEntity;
import coupon.coupon.repository.IssuedCouponRepository;
import coupon.member.repository.MemberRepository;
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
