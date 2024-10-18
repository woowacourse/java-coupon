package coupon.service;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.IssuedCoupon;
import coupon.domain.member.Member;
import coupon.repository.CouponEntity;
import coupon.repository.CouponRepository;
import coupon.repository.IssuedCouponEntity;
import coupon.repository.IssuedCouponRepository;
import coupon.repository.MemberEntity;
import coupon.repository.MemberRepository;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponIssueService {

    private final CouponRepository couponRepository;
    private final IssuedCouponRepository issuedCouponRepository;
    private final MemberRepository memberRepository;

    public IssuedCouponEntity issueCoupon(long memberId, long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 쿠폰입니다."))
                .toDomain();
        memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));

        IssuedCoupon issuedCoupon = new IssuedCoupon(coupon, LocalDateTime.now());
        return issuedCouponRepository.save(IssuedCouponEntity.from(memberId, issuedCoupon));
    }
}
