package coupon.membercoupon;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Getter
public class MemberCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private long couponId;

    @Column(nullable = false)
    private long memberId;

    @Column(nullable = false)
    private boolean isUsed;

    @Column(nullable = false)
    private LocalDateTime issuedAt;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    public MemberCoupon(long couponId, long memberId, LocalDateTime issuedAt) {
        validateIssuedAt(issuedAt);
        this.couponId = couponId;
        this.memberId = memberId;
        this.isUsed = false;
        this.issuedAt = issuedAt;
        this.expiredAt = issuedAt.plusDays(7);
    }

    private void validateIssuedAt(LocalDateTime issuedAt) {
        if (issuedAt.isBefore(LocalDate.now().atStartOfDay())) {
            throw new IllegalArgumentException(
                    String.format("이미 지난 날을 발급일로 설정할 수 없습니다. : %s", issuedAt));
        }
    }
}
