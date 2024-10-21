package coupon.member.domain;

import lombok.Getter;

@Getter
public class Name {
    private static final int NAME_LENGTH = 20;

    private final String name;

    public Name(String name) {
        validateNameValue(name);
        validateNameLength(name);
        this.name = name;
    }

    private void validateNameValue(String name) {
        if (name == null) {
            throw new IllegalArgumentException("이름은 null일 수 없습니다.");
        }

        if (name.isEmpty()) {
            throw new IllegalArgumentException("이름은 빈 값일 수 없습니다.");
        }
    }

    private void validateNameLength(String name) {
        if (name.length() > NAME_LENGTH) {
            throw new IllegalArgumentException("이름은 [%d]자를 넘을 수 없습니다.".formatted(NAME_LENGTH));
        }
    }
}
