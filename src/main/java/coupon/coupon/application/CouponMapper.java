package coupon.coupon.application;

import coupon.common.domain.Money;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponCategory;
import org.springframework.stereotype.Component;

@Component
public class CouponMapper {

    public CouponResponse toResponse(Coupon coupon) {
        return new CouponResponse(
                coupon.getId(),
                coupon.getName().getValue(),
                coupon.getDiscountAmount().getValue().longValue(),
                coupon.getMinOrderAmount().getValue().longValue(),
                coupon.getCategory().name(),
                coupon.getIssueDate().getIssueStartedAt(),
                coupon.getIssueDate().getIssueEndedAt()
        );
    }

    public Coupon toCoupon(CreateCouponRequest request) {
        return new Coupon(
                request.name(),
                Money.wons(request.discountAmount()),
                Money.wons(request.minOrderAmount()),
                CouponCategory.from(request.category()),
                request.issueStartDate(),
                request.issueEndDate()
        );
    }
}
