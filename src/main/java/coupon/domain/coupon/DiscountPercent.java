package coupon.domain.coupon;


import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiscountPercent {

    private static final int MINIMUM_PERCENT = 3;
    private static final int MAXIMUM_PERCENT = 20;

    private int percent;

    public DiscountPercent(int percent) {
        validatePercent(percent);
        this.percent = percent;
    }

    private void validatePercent(int percent) {
        if (percent < MINIMUM_PERCENT || percent > MAXIMUM_PERCENT) {
            String message = "할인율은 %d 이상 %d 이하여야 합니다. 입력 값: ".formatted(MINIMUM_PERCENT, MAXIMUM_PERCENT);
            throw new IllegalArgumentException(message + percent);
        }
    }
}
