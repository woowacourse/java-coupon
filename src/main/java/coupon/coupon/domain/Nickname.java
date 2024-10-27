package coupon.coupon.domain;

import java.util.Objects;
import jakarta.persistence.Column;
import coupon.coupon.CouponException;

public class Nickname {

    private static final int MAX_LENGTH = 10;
    public static final String NO_NICKNAME_MESSAGE = "회원명이 누락되었습니다.";
    private static final String NAME_LENGTH_MESSAGE = "회원명은 10자 이하로 설정해주세요.";

    @Column(nullable = false, length = MAX_LENGTH)
    private final String nickname;

    public Nickname(String nickname) {
        validate(nickname);
        this.nickname = nickname;
    }

    private void validate(String name) {
        validateNull(name);
        validateNameLength(name);
    }

    private void validateNull(String name) {
        if (Objects.isNull(name)) {
            throw new CouponException(NO_NICKNAME_MESSAGE);
        }
    }

    private void validateNameLength(String name) {
        if (name.isBlank() || name.length() > MAX_LENGTH) {
            throw new CouponException(NAME_LENGTH_MESSAGE);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Nickname nickname1 = (Nickname) o;
        return Objects.equals(nickname, nickname1.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname);
    }
}
