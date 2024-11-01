package coupon.domain.coupon;

import jakarta.persistence.Embeddable;

@Embeddable
public class Name {

    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 30;

    private String name;

    public Name(String name) {
        String trimName = name.trim();
        validateLength(trimName);
        this.name = trimName;
    }

    protected Name() {
    }

    private void validateLength(String trimName) {
        if (trimName.length() < MIN_LENGTH || trimName.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(String.format("쿠폰의 이름은 공백을 제외하고 %d자 이상 %d자 이하여야 합니다.", MIN_LENGTH, MAX_LENGTH));
        }
    }

    public String getName() {
        return name;
    }
}
