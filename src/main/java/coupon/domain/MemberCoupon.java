package coupon.domain;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {

    private static final int USABLE_DAY = 6;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coupon_id", nullable = false)
    private Long couponId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "is_used", nullable = false)
    private Boolean isUsed;

    @Column(name = "issued_at", nullable = false)
    private LocalDate issuedAt;

    @Column(name = "expires_at", nullable = true)
    private LocalDate expiresAt;

    public static MemberCoupon issue(Long couponId, Long memberId) {
        LocalDate today = LocalDate.now();
        LocalDate expiresAt = today.plusDays(USABLE_DAY);

        return new MemberCoupon(null, couponId, memberId, false, today, expiresAt);
    }

    public void use() {
        validateAlreadyUsed();
        validateUsableDate();

        this.isUsed = true;
    }

    private void validateAlreadyUsed() {
        if (isUsed) {
            throw new IllegalStateException("이미 사용된 쿠폰입니다.");
        }
    }

    private void validateUsableDate() {
        LocalDate now = LocalDate.now();

        if (now.isAfter(expiresAt)) {
            throw new IllegalStateException("사용 기간이 지난 쿠폰입니다.");
        }
    }
}
