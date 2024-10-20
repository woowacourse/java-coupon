package coupon.domain;

public class Name {
    private static final int MAX_NAME_LENGTH = 30;

    private final String name;

    public Name(String name) {
        validateEmptyString(name);
        validateStringLength(name);
        this.name = name;
    }

    private void validateEmptyString(String name) {
        if (name == null) {
            throw new IllegalArgumentException("이름은 null일 수 없습니다.");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("이름은 빈 값일 수 없습니다.");
        }
    }

    private void validateStringLength(String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("이름은 %d자를 초과할 수 없습니다.".formatted(MAX_NAME_LENGTH));
        }
    }

    public String getName() {
        return name;
    }
}
