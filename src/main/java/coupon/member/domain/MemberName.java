package coupon.member.domain;

import lombok.Getter;

@Getter
public class MemberName {

    private static final int MINIMUM_ENABLE_LENGTH = 2;
    private static final int MAXIMUM_ENABLE_LENGTH = 10;

    private final String value;

    public MemberName(final String value) {
        validateValue(value);
        this.value = value;
    }

    private void validateValue(final String value) {
        validateValueIsNull(value);
        validateValueLength(value);
    }

    private void validateValueIsNull(final String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("회원 이름 값으로 null 혹은 공백이 입력될 수 없습니다.");
        }
    }

    private void validateValueLength(final String value) {
        if (value.length() < MINIMUM_ENABLE_LENGTH || value.length() > MAXIMUM_ENABLE_LENGTH) {
            throw new IllegalArgumentException("회원 이름 값은 " + MINIMUM_ENABLE_LENGTH + "이상, " + MAXIMUM_ENABLE_LENGTH + "이하의 길이만 가능합니다. - " + value);
        }
    }
}
