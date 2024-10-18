package coupon.domain.coupon;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class IssueDuration {

    private final LocalDate startDateTime;
    private final LocalDate endDateTime;

    public IssueDuration(LocalDate startDateTime, LocalDate endDateTime) {
        validateIssueDuration(startDateTime, endDateTime);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    private void validateIssueDuration(LocalDate startDateTime, LocalDate endDateTime) {
        if (startDateTime.isAfter(endDateTime)) {
            throw new IllegalArgumentException("발급 시작일이 종료일보다 늦을 수 없습니다.");
        }
    }
}
