package coupon.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class UserCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long couponId;

    @ManyToOne
    private User user;

    private boolean active;

    private LocalDateTime issuedAt;

    private LocalDateTime expiresAt;

    public UserCoupon(Long couponId, User user, boolean active, LocalDateTime issuedAt, LocalDateTime expiresAt) {
        this.couponId = couponId;
        this.user = user;
        this.active = active;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
    }
}
