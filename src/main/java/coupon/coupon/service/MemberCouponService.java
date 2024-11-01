package coupon.coupon.service;

import coupon.coupon.domain.MemberCoupon;
import coupon.coupon.domain.MemberCouponRepository;
import coupon.coupon.dto.CouponResponse;
import coupon.coupon.dto.MemberCouponResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberCouponService {

    private static final int MAX_COUPON_ISSUE_LIMIT = 5;

    private final MemberCouponRepository memberCouponRepository;
    private final CouponService couponService;

    @Transactional
    public void issueCoupon(long couponId, long memberId, LocalDateTime issuedAt) {
        validateIssuance(couponId, memberId);
        validateIssuedAt(couponId, issuedAt);

        MemberCoupon memberCoupon = new MemberCoupon(couponId, memberId, issuedAt);
        memberCouponRepository.save(memberCoupon);
    }

    private void validateIssuance(long couponId, long memberId) {
        int issuedCouponCount = memberCouponRepository.countByCouponIdAndMemberId(couponId, memberId);
        if (issuedCouponCount >= MAX_COUPON_ISSUE_LIMIT) {
            throw new IllegalArgumentException(String.format("%d장 이상의 쿠폰을 발급할 수 없습니다.", MAX_COUPON_ISSUE_LIMIT));
        }
    }

    private void validateIssuedAt(long couponId, LocalDateTime issuedAt) {
        CouponResponse coupon = couponService.findById(couponId);
        if (issuedAt.isBefore(coupon.startDate()) || issuedAt.isAfter(coupon.endDate())) {
            throw new IllegalArgumentException("쿠폰의 시작일, 만료일 내에 발급되어야 합니다.");
        }
    }

    public List<MemberCouponResponse> findMemberCouponsByMemberId(long memberId) {
        return memberCouponRepository.findAllByMemberId(memberId)
                .stream()
                .map(this::mapToMemberCouponResponse)
                .toList();
    }

    private MemberCouponResponse mapToMemberCouponResponse(MemberCoupon memberCoupon) {
        CouponResponse coupon = couponService.findById(memberCoupon.getCouponId());

        return MemberCouponResponse.of(memberCoupon, coupon);
    }
}
