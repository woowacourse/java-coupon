package coupon.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "member_coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class MemberCoupon {

    @Id
    @GeneratedValue
    private UUID id;

    private Long memberId;

    private UUID couponId;

    private boolean isUsed;

    private LocalDateTime issuedAt;

    private LocalDateTime expiredAt;

    public MemberCoupon(final UUID couponId, final long memberId, final boolean isUsed, final LocalDateTime issuedAt, final LocalDateTime expiredAt) {
        this.couponId = couponId;
        this.memberId = memberId;
        this.isUsed = isUsed;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
    }
}
