package coupon.domain;

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
    private LocalDate issuedAt;

    @Column(nullable = false)
    private LocalDate expiredAt;

    public IssuancePeriod(LocalDate issuedAt, LocalDate expiredAt) {
        validatePeriod(issuedAt, expiredAt);
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
    }

    private void validatePeriod(LocalDate issuedAt, LocalDate expiredAt) {
        if (issuedAt.isAfter(expiredAt) || issuedAt.isEqual(expiredAt)) {
            throw new CouponException("발급일은 만료일 보다 같거나 이전이어야 합니다.");
        }
    }

    public boolean isIssuable(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        return !issuedAt.isAfter(date) && !expiredAt.isBefore(date);
    }

    public boolean isExpired(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        return expiredAt.isBefore(date);
    }
}
