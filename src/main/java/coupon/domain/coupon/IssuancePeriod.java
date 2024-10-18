package coupon.domain.coupon;

import coupon.exception.CouponException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IssuancePeriod {

    @Column(nullable = false)
    private LocalDate issuanceStart;

    @Column(nullable = false)
    private LocalDate issuanceEnd;

    public IssuancePeriod(LocalDate issuanceStart, LocalDate issuanceEnd) {
        validatePeriod(issuanceStart, issuanceEnd);
        this.issuanceStart = issuanceStart;
        this.issuanceEnd = issuanceEnd;
    }

    private void validatePeriod(LocalDate issuanceStart, LocalDate issuanceEnd) {
        if (issuanceStart.isAfter(issuanceEnd) || issuanceStart.isEqual(issuanceEnd)) {
            throw new CouponException("발급일은 만료일 보다 같거나 이전이어야 합니다.");
        }
    }

    public boolean isIssuable(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        return !issuanceStart.isAfter(date) && !issuanceEnd.isBefore(date);
    }
}
