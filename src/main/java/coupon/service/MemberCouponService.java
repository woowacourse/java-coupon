package coupon.service;

import coupon.CouponException;
import coupon.dto.CouponIssueRequest;
import coupon.dto.MemberCouponResponse;
import coupon.dto.MemberCouponResponses;
import coupon.entity.Coupon;
import coupon.entity.MemberCoupon;
import coupon.repository.MemberCouponRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private static final int ISSUE_LIMIT = 5;
    private final CouponService couponService;
    private final MemberCouponRepository memberCouponRepository;

    @Transactional
    public MemberCoupon issue(CouponIssueRequest request) {
        Coupon coupon = couponService.read(request.couponId());
        validate(coupon, request.memberId());
        MemberCoupon memberCoupon = new MemberCoupon(
                request.couponId(),
                request.memberId(),
                request.start()
        );
        return memberCouponRepository.save(memberCoupon);
    }

    private void validate(Coupon coupon, long memberId) {
        if (coupon.isExpired()) {
            throw new CouponException("coupon expired");
        }
        if (memberCouponRepository.countByCouponIdAndMemberId(coupon.getId(), memberId) >= ISSUE_LIMIT) {
            throw new CouponException("cannot issue same coupon to same member more than " + ISSUE_LIMIT);
        }
    }

    @Transactional(readOnly = true)
    public MemberCouponResponses findByMemberId(long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMemberId(memberId);

        List<MemberCouponResponse> memberCouponResponses = memberCoupons.stream()
                .map(this::getMemberCouponResponse)
                .toList();
        return new MemberCouponResponses(memberCouponResponses);
    }

    private MemberCouponResponse getMemberCouponResponse(MemberCoupon memberCoupon) {
        Coupon coupon = couponService.read(memberCoupon.getCouponId());
        return MemberCouponResponse.from(memberCoupon, coupon);
    }
}
