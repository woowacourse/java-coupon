package coupon.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class UserCouponEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private long userId;

    private long couponId;

    private boolean used;

    private LocalDateTime usedDateTime;

    private LocalDateTime expiredDateTime;

    public UserCouponEntity(long userId, long couponId, boolean used, LocalDateTime expiredDateTime) {
        this.userId = userId;
        this.couponId = couponId;
        this.used = used;
        this.expiredDateTime = expiredDateTime;
    }
}
