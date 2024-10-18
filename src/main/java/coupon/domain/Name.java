package coupon.domain;

public class Name {

    private static final int MAX_NAME_LENGTH = 30;

    private final String name;

    public Name(String name) {
        validateLength(name);
        this.name = name;
    }

    private void validateLength(String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("최대 이름 길이를 초과합니다.");
        }
    }
}
