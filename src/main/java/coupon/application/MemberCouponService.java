package coupon.application;

import coupon.domain.MemberCoupon;
import coupon.domain.MemberCouponRepository;
import coupon.dto.CouponResponse;
import coupon.dto.MemberCouponRequest;
import coupon.dto.MemberCouponResponse;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberCouponService {

    private static final int MAX_MEMBER_COUPON_COUNT = 5;

    private final MemberCouponRepository memberCouponRepository;
    private final CouponService couponService;

    @Transactional
    public void create(MemberCouponRequest memberCouponRequest) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findByCouponIdAndMemberId(
                memberCouponRequest.couponId(), memberCouponRequest.memberId());

        validateMemberCouponsCount(memberCoupons);
        validateCouponIssuancePeriod(memberCouponRequest);

        MemberCoupon memberCoupon = memberCouponRequest.toMemberCoupon();
        memberCouponRepository.save(memberCoupon);
    }

    private void validateMemberCouponsCount(List<MemberCoupon> memberCoupons) {
        if (memberCoupons.size() >= MAX_MEMBER_COUPON_COUNT) {
            throw new IllegalArgumentException("Too many coupons");
        }
    }

    private void validateCouponIssuancePeriod(MemberCouponRequest memberCouponRequest) {
        CouponResponse coupon = couponService.findCoupon(memberCouponRequest.couponId());
        LocalDate issueDate = memberCouponRequest.issueDate();
        if (issueDate.isBefore(coupon.startDate()) || issueDate.isAfter(coupon.endDate())) {
            throw new IllegalArgumentException("Invalid issuance period");
        }
    }

    @Transactional(readOnly = true)
    public List<MemberCouponResponse> findByMemberId(Long memberId) {
        return memberCouponRepository.findByMemberId(memberId).stream()
                .map(this::getMemberCouponResponse)
                .toList();
    }

    private MemberCouponResponse getMemberCouponResponse(MemberCoupon memberCoupon) {
        Long couponId = memberCoupon.getCouponId();
        CouponResponse couponResponse = couponService.findCoupon(couponId);

        return MemberCouponResponse.of(memberCoupon, couponResponse);
    }
}
