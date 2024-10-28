package coupon.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ValidityPeriod {

    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
}
