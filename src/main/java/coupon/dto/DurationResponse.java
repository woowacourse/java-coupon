package coupon.dto;

import java.time.LocalDate;

public record DurationResponse(
        LocalDate startDate,
        LocalDate endDate) {
}
