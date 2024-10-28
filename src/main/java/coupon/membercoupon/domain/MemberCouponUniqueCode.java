package coupon.membercoupon.domain;

import coupon.membercoupon.util.MemberCouponUniqueCodeGenerator;

public class MemberCouponUniqueCode {

    private final String value;

    public MemberCouponUniqueCode(final MemberCouponUniqueCodeGenerator memberCouponUniqueCodeGenerator) {
        this(memberCouponUniqueCodeGenerator.generate());
    }

    public MemberCouponUniqueCode(final String value) {
        validateValue(value);
        this.value = value;
    }

    private void validateValue(final String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("회원 쿠폰 유니코 코드 값은 null 혹은 공백이 입력될 수 없습니다.");
        }
    }
}
