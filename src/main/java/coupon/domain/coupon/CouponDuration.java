package coupon.domain.coupon;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponDuration {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public CouponDuration(LocalDateTime startDate, LocalDateTime endDate) {
        validate(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validate(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작 날짜는 종료 날짜보다 이전만 가능합니다.");
        }
    }
}
