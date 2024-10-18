package coupon;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "member_coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberCouponEntity {
    @Id
    @GeneratedValue
    private Long id;

    private UUID couponId;
    private UUID memberId;
    private boolean isUsed;
    private LocalDateTime issuedAt;
    private LocalDateTime expiredAt;
}
