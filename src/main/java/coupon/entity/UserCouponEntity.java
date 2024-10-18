package coupon.entity;

import static jakarta.persistence.GenerationType.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
}
