package coupon.coupon.domain;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class IssuableDuration {

    private LocalDate startDate;

    private LocalDate endDate;

    public IssuableDuration(LocalDate startDate, LocalDate endDate) {
        validate();
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validate() {
        if(startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
    }
}
