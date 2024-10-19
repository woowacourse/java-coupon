package coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MemberCoupon {

    private static final int OFFSET_DAYS = 6;
    private static final int HOUR = 23;
    private static final int MINUTE = 59;
    private static final int SECOND = 59;
    private static final int NANO_OF_SECOND = 999_999_999;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coupon_id")
    private Long couponId;

    @Column(name = "is_used")
    private boolean isUsed;

    @Column(name = "issued_date_time")
    private LocalDateTime issuedDateTime;

    @Column(name = "expiration_date_time")
    private LocalDateTime expirationDateTime;

    public MemberCoupon(Long couponId, boolean isUsed, LocalDateTime issuedDateTime) {
        this.couponId = couponId;
        this.isUsed = isUsed;
        this.issuedDateTime = issuedDateTime;
        this.expirationDateTime = issuedDateTime.toLocalDate().plusDays(OFFSET_DAYS).atTime(HOUR, MINUTE, SECOND, NANO_OF_SECOND);
    }
}
