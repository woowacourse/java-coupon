package coupon.domain.coupon;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Period {

    @Column(name = "START_AT", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "END_AT", nullable = false)
    private LocalDateTime endAt;

    public Period(final LocalDateTime startAt, final LocalDateTime endAt) {
        validate(startAt, endAt);
        this.startAt = startAt;
        this.endAt = endAt;
    }

    private void validate(final LocalDateTime startAt, final LocalDateTime endAt) {
        if (startAt.isAfter(endAt)) {
            throw new IllegalArgumentException("시작일이 종료일보다 뒤에 있을 수 없습니다.");
        }
    }
}
