package coupon.coupon.domain;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;

@Getter
public class IssuedCoupon {

    private static final Duration USABLE_PERIOD = Duration.ofDays(7);

    private final Coupon coupon;
    private final LocalDateTime issuedAt;
    private final DatePeriod usablePeriod;
    private final boolean used;

    private IssuedCoupon(Coupon coupon, LocalDateTime issuedAt, DatePeriod usablePeriod, boolean used) {
        this.coupon = coupon;
        this.issuedAt = issuedAt;
        this.usablePeriod = usablePeriod;
        this.used = used;
    }

    public IssuedCoupon(Coupon coupon, LocalDateTime issuedAt) {
        Objects.requireNonNull(coupon, "쿠폰은 필수입니다.");
        Objects.requireNonNull(issuedAt, "발급 시간은 필수입니다.");

        if (!coupon.isIssuable(issuedAt)) {
            throw new IllegalArgumentException("발급할 수 없는 쿠폰입니다.");
        }
        LocalDate issuedDate = issuedAt.toLocalDate();
        this.issuedAt = issuedAt;
        this.coupon = coupon;
        this.usablePeriod = new DatePeriod(issuedDate, issuedDate.plus(USABLE_PERIOD));
        this.used = false;
    }

    public IssuedCoupon use() {
        return new IssuedCoupon(coupon, issuedAt, usablePeriod, true);
    }

    public boolean isUsable(LocalDateTime currentTime) {
        return !used && usablePeriod.isBetweenPeriod(currentTime);
    }

    public LocalDateTime getExpiredAt() {
        return usablePeriod.getEndDateTime();
    }
}
