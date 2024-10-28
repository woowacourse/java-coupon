package coupon.domain.time;

import java.time.LocalDateTime;

@FunctionalInterface
public interface TimeSupplier {
    LocalDateTime supply();
}
