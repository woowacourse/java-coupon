package coupon.domain.coupon;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import coupon.exception.CouponException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class IssueDuration {

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    public IssueDuration(LocalDateTime startAt, LocalDateTime endAt) {
        validatePeriod(startAt, endAt);
        this.startAt = startAt;
        this.endAt = endAt;
    }

    private void validatePeriod(LocalDateTime startAt, LocalDateTime endAt) {
        if (endAt.isBefore(startAt) || endAt.isEqual(startAt)) {
            throw new CouponException("쿠폰 발급 종료일은 시작일 보다 이후 입니다.");
        }
    }

    boolean isWithin(LocalDateTime localDateTime) {
        LocalDate date = localDateTime.toLocalDate();
        LocalDate startDate = startAt.toLocalDate();
        LocalDate endDate = endAt.toLocalDate();
        return (startDate.isEqual(date) || startDate.isBefore(date)) &&
                (endDate.isEqual(date) || endDate.isAfter(date));
    }
}
