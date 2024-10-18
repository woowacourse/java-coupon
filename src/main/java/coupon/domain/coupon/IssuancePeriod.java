package coupon.domain.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class IssuancePeriod {

    @Column(name = "startAt", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "endAt", nullable = false)
    @NotNull
    private LocalDateTime endAt;
}
