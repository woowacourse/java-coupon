package coupon.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Getter;

@Getter
public class IssuablePeriod {

    private static final LocalTime START_TIME = LocalTime.of(0, 0);
    private static final LocalTime END_TIME = LocalTime.of(23, 59, 59, 999_999);

    private final LocalDate issuanceDate;
    private final LocalDate expirationDate;

    public IssuablePeriod(LocalDate issuanceDate, LocalDate expirationDate) {
        if (issuanceDate == null || expirationDate == null) {
            throw new IllegalArgumentException("발급일과 만료일은 필수입니다.");
        }
        if (issuanceDate.isAfter(expirationDate)) {
            throw new IllegalArgumentException("발급일은 만료일 이전이어야 합니다.");
        }
        this.issuanceDate = issuanceDate;
        this.expirationDate = expirationDate;
    }

    public boolean isIssuable(LocalDateTime time) {
        LocalDateTime startPeriod = issuanceDate.atTime(START_TIME);
        LocalDateTime endPeriod = expirationDate.atTime(END_TIME);
        if (startPeriod.isEqual(time) || endPeriod.isEqual(time)) {
            return true;
        }
        return time.isAfter(startPeriod) && time.isBefore(endPeriod);
    }
}
