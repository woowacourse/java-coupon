package coupon.domain.coupon;

import lombok.Getter;

@Getter
public class MinimumMount {

    private static final int MINIMUM_MOUNT = 5_000;
    private static final int MAXIMUM_MOUNT = 100_000;

    private final int mount;

    public MinimumMount(int mount) {
        validate(mount);
        this.mount = mount;
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
