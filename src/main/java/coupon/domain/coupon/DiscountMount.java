package coupon.domain.coupon;

import lombok.Getter;

@Getter
public class DiscountMount {

    private static final int MINIMUM_MOUNT = 1_000;
    private static final int MAXIMUM_MOUNT = 10_000;
    private static final int UNIT = 500;

    private final int mount;

    public DiscountMount(int mount) {
        validate(mount);
        this.mount = mount;
    }

    private void validate(int mount) {
        if (mount < MINIMUM_MOUNT) {
            throw new IllegalArgumentException("할인 금액은 " + MINIMUM_MOUNT + "원 이상이어야 합니다.");
        }
        if (mount > MAXIMUM_MOUNT) {
            throw new IllegalArgumentException("할인 금액은 " + MAXIMUM_MOUNT + "원 이하여야 합니다.");
        }
        if (mount % UNIT != 0) {
            throw new IllegalArgumentException("할인 금액은 " + UNIT + "원 단위여야 합니다.");
        }
    }
}
