package coupon.coupon.domain;

import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Getter;

@Getter
public class IssuePeriod {

    private static final LocalTime MIN_TIME = LocalTime.of(0, 0, 0);
    private static final LocalTime MAX_TIME = LocalTime.of(23, 59, 59);
    private final LocalDateTime issuedAt;
    private final LocalDateTime expiresAt;


    public IssuePeriod(LocalDateTime issuedAt, LocalDateTime expiresAt) {
        validate(issuedAt, expiresAt);
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
    }

    private void validate(LocalDateTime issuedAt, LocalDateTime expiresAt) {
        if (issuedAt == null || expiresAt == null) {
            throw new IllegalArgumentException("시작일과 종료일은 반드시 존재해야 한다.");
        }
        validateTime(issuedAt);
        validateTime(expiresAt);
        if (!issuedAt.isBefore(expiresAt)) {
            throw new IllegalArgumentException("시작일은 종료일보다 이전이어야 한다.");
        }
    }

    private void validateTime(LocalDateTime time) {
        if (time.toLocalTime().isBefore(MIN_TIME)) {
            throw new IllegalArgumentException("시간은 " + MIN_TIME + " 이상이어야 한다.");
        }
        if (time.toLocalTime().isAfter(MAX_TIME)) {
            throw new IllegalArgumentException("시간은 " + MAX_TIME + " 이하여야 한다.");
        }
    }
}
