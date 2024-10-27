package coupon.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class CouponIssuanceDate {

    private final LocalDateTime issuanceStartDate;
    private final LocalDateTime issuanceEndDate;

    public CouponIssuanceDate(LocalDateTime issuanceStartDate, LocalDateTime issuanceEndDate) {
        validateIssuanceDate(issuanceStartDate.with(LocalTime.MIN), issuanceEndDate.with(LocalTime.MAX));
        this.issuanceStartDate = issuanceStartDate.with(LocalTime.MIN);
        this.issuanceEndDate = issuanceEndDate.with(LocalTime.MAX);
    }

    private void validateIssuanceDate(LocalDateTime issuanceStartDate, LocalDateTime issuanceEndDate) {
        if (!issuanceEndDate.isEqual(issuanceStartDate) && !issuanceEndDate.isAfter(issuanceStartDate)) {
            throw new IllegalArgumentException("Issuance End Date cannot be earlier than Issuance Start Date");
        }
    }
}
