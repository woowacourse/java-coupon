package coupon.domain.coupon;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MinimumMount {

    private static final int MINIMUM_MOUNT = 5_000;
    private static final int MAXIMUM_MOUNT = 100_000;

    private int minimumMount;

    public MinimumMount(int minimumMount) {
        validate(minimumMount);
        this.minimumMount = minimumMount;
    }

    private void validate(int mount) {
        if (mount < MINIMUM_MOUNT) {
            throw new IllegalArgumentException("최소 주문 금액은 " + MINIMUM_MOUNT + "원 이상이어야 합니다.");
        }
        if (mount > MAXIMUM_MOUNT) {
            throw new IllegalArgumentException("최소 주문 금액은 " + MAXIMUM_MOUNT + "원 이하여야 합니다.");
        }
    }
}
