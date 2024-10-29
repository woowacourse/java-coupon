package coupon.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Getter
public class IssuancePeriod {

    @Column(name = "start_date", nullable = false)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endDate;

    public IssuancePeriod(LocalDateTime startDate, LocalDateTime endDate) {
        validate(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validate(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작일은 종료일보다 이전이어야 합니다.");
        }
    }
}
