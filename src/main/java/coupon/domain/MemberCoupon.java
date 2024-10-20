package coupon.domain;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MemberCoupon {

    private static final int EXPIRATION_DURATION = 6;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long couponId;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private boolean used;

    @Column(nullable = false)
    private LocalDate issueDate;

    @Column(nullable = false)
    private LocalDate expirationDate;

    public MemberCoupon(Long couponId, Long memberId, LocalDate issueDate) {
        this.couponId = couponId;
        this.memberId = memberId;
        this.used = false;
        this.issueDate = issueDate;
        this.expirationDate = issueDate.plusDays(EXPIRATION_DURATION);
    }
}
