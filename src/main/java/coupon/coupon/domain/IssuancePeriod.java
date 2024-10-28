package coupon.coupon.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Getter;

@Getter
public class IssuancePeriod {

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private final LocalDateTime endDate;

    public IssuancePeriod(final LocalDateTime startDate, final LocalDateTime endDate) {
        validateDateIsNull(startDate, endDate);
        validateEndDateIsBeforeThenStartDate(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validateDateIsNull(final LocalDateTime startDate, final LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("시작일 혹은 종료일은 null 값을 입력할 수 없습니다.");
        }
    }

    private void validateEndDateIsBeforeThenStartDate(final LocalDateTime startDate, final LocalDateTime endDate) {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("종료일이 시작일보다 이전일 수 없습니다. - " + startDate + ", " + endDate);
        }
    }
}
