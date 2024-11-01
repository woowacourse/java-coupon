package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.IssuedCoupon;
import coupon.domain.Member;
import coupon.dto.IssuedCouponResponse;
import coupon.repository.IssuedCouponRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IssuedCouponService {

    private static final int MAX_ISSUABLE_COUPON_SIZE = 5;

    private final MemberService memberService;
    private final IssuedCouponRepository issuedCouponRepository;
    private final CouponService couponService;

    @Transactional
    public IssuedCoupon issue(Long memberId, Long couponId) {
        Coupon coupon = couponService.getCoupon(couponId);
        Member member = memberService.getMember(memberId);
        validateIssuedCouponNumber(coupon, member);
        IssuedCoupon issue = IssuedCoupon.issue(coupon, member);
        return issuedCouponRepository.save(issue);
    }

    private void validateIssuedCouponNumber(Coupon coupon, Member member) {
        List<IssuedCoupon> issuedCouponsByMember = issuedCouponRepository.findAllByCouponIdAndMember(coupon.getId(),
                member);
        if (issuedCouponsByMember.size() >= MAX_ISSUABLE_COUPON_SIZE) {
            throw new IllegalArgumentException("쿠폰은 최대 %d장 발급할 수 있습니다.".formatted(MAX_ISSUABLE_COUPON_SIZE));
        }
    }

    public List<IssuedCouponResponse> getIssuedCouponBy(Long memberId) {
        List<IssuedCoupon> issuedCoupons = issuedCouponRepository.findAllByMemberId(memberId);
        return issuedCoupons.stream()
                .map(issuedCoupon -> IssuedCouponResponse.from(
                        couponService.getCoupon(issuedCoupon.getCouponId()),
                        issuedCoupon
                ))
                .toList();
    }
}
