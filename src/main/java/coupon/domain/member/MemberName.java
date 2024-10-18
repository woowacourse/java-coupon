package coupon.domain.member;

import coupon.common.ErrorConstant;
import coupon.common.exception.CouponException;
import lombok.Getter;

@Getter
public class MemberName {

    private final String name;

    public MemberName(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        if (name == null || name.isBlank()) {
            throw new CouponException(ErrorConstant.MEMBER_NAME_IS_NULL_OR_EMPTY);
        }
    }
}
