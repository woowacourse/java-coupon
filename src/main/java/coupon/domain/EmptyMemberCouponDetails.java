package coupon.domain;

import lombok.Getter;

@Getter
public class EmptyMemberCouponDetails extends MemberCouponDetails {

    public EmptyMemberCouponDetails() {
        super(new MemberCoupon(null, null, null, false, null, null),
                null);
    }

    @Override
    public boolean isNotEmpty() {
        return false;
    }
}
