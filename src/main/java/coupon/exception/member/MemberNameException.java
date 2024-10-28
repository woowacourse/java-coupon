package coupon.exception.member;

import coupon.exception.coupon.CouponException;

public class MemberNameException extends CouponException {

    public MemberNameException(String name) {
        super("회원 이름은 반드시 존재해야 하고, 15자 이하여야 합니다. 입력된 이름: " + name);
    }
}
