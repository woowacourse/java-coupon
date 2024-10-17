package coupon.coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponName {

    private static final int MAX_NAME_LENGTH = 30;

    @Column(name = "name", nullable = false)
    private String value;

    public CouponName(String name) {
        String strippedName = name.strip();

        validateNameLength(strippedName);

        this.value = strippedName;
    }

    public void validateNameLength(String name) {
        if (name.isBlank() || name.length() > MAX_NAME_LENGTH) {
            String message = "쿠폰 이름은 %d자 이하의 비어 있지 않은 문자열이어야 합니다.".formatted(MAX_NAME_LENGTH);

            throw new IllegalArgumentException(message);
        }
    }
}
