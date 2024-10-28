package coupon.service.dto;

import coupon.domain.Coupon;
import coupon.domain.MemberCoupon;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MemberCouponResponses {

    public List<MemberCouponResponse> memberCouponResponses;

    public MemberCouponResponses(List<MemberCouponResponse> memberCouponResponses) {
        this.memberCouponResponses = memberCouponResponses;
    }

    public MemberCouponResponses(Map<MemberCoupon, Coupon> result) {
        this.memberCouponResponses = result.entrySet().stream()
                .map(entry -> new MemberCouponResponse(
                        entry.getValue().getId(), // couponId
                        entry.getValue().getCouponName().getName(), // couponName
                        entry.getValue().getDiscountAmount().getAmount(), // discountAmount
                        entry.getValue().getMinimumOrderAmount().getAmount(), // minimOrderAmount
                        entry.getValue().getCouponCategory().name(), // couponCategory
                        entry.getKey().isUsed(), // used
                        entry.getValue().getCouponPeriod().getIssueStartedAt(), // issueStartedAt
                        entry.getValue().getCouponPeriod().getIssueEndedAt(), // issueEndedAt
                        entry.getKey().getIssuedAt(), // issuedAt
                        entry.getKey().getUseEndedAt() // useEndedAt
                ))
                .collect(Collectors.toList());
    }
}
