package coupon.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCoupon {

    private static final int DEFAULT_AVAILABLE_DAYS = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Coupon coupon;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    private boolean used;

    @NotNull
    @CreatedDate
    private LocalDateTime issuedAt;

    @NotNull
    private LocalDateTime expiresAt;

    public UserCoupon(Coupon coupon, User user) {
        this.coupon = coupon;
        this.user = user;
        this.used = false;
        this.expiresAt = issuedAt.toLocalDate()
                .plusDays(DEFAULT_AVAILABLE_DAYS)
                .atStartOfDay();
    }

    @AssertTrue
    public boolean validateIssuedAt() {
        return issuedAt.isBefore(expiresAt);
    }
}
