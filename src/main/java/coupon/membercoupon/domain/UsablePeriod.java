package coupon.membercoupon.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UsablePeriod {

    private static final int VALUE_FOR_DAYS_TO_ADD = 6;

    private LocalDate issuanceDate;
    private LocalDate expirationDate;

    public UsablePeriod(LocalDate issuanceDate) {
        this(issuanceDate, issuanceDate.plusDays(VALUE_FOR_DAYS_TO_ADD));
    }

    public UsablePeriod(LocalDate issuanceDate, LocalDate expirationDate) {
        this.issuanceDate = issuanceDate;
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsablePeriod that = (UsablePeriod) o;
        return Objects.equals(issuanceDate, that.issuanceDate) && Objects.equals(expirationDate, that.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(issuanceDate, expirationDate);
    }
}
