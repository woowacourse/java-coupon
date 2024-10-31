package coupon.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ValidityPeriod implements Serializable {

    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
}
