package coupon.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CouponIssuanceDate {

    private final LocalDateTime issuanceStartDate;
    private final LocalDateTime issuanceEndDate;

    public CouponIssuanceDate(LocalDateTime issuanceStartDate, LocalDateTime issuanceEndDate) {
        validateIssuanceDate(issuanceStartDate, issuanceEndDate);
        this.issuanceStartDate = issuanceStartDate;
        this.issuanceEndDate = issuanceEndDate;
    }

    private void validateIssuanceDate(LocalDateTime issuanceStartDate, LocalDateTime issuanceEndDate) {
        if (!issuanceEndDate.isEqual(issuanceStartDate) && !issuanceEndDate.isAfter(issuanceStartDate)) {
            throw new IllegalArgumentException("Issuance End Date cannot be earlier than Issuance Start Date");
        }
    }
}
