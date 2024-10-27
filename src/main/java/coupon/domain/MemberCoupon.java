package coupon.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long couponId;
    private Long memberId;
    private boolean isUsed;
    private LocalDate issueDate;
    private LocalDate expirationDate;

    protected MemberCoupon(Long id, Long couponId, Long memberId, boolean isUsed, LocalDate issueDate,
                           LocalDate expirationDate) {
        this.id = id;
        this.couponId = couponId;
        this.memberId = memberId;
        this.isUsed = isUsed;
        this.issueDate = issueDate;
        this.expirationDate = expirationDate;
    }

    public static MemberCoupon issue(Long couponId, Long memberId) {
        LocalDate today = LocalDate.now();
        return new MemberCoupon(null, couponId, memberId, false, today, today.plusDays(6));
    }

    public void use() {
        validatePeriodOfUse();
        validateAlreadyUsed();
        isUsed = true;
    }

    private void validateAlreadyUsed() {
        if (isUsed) {
            throw new IllegalStateException("이미 사용된 쿠폰입니다.");
        }
    }

    private void validatePeriodOfUse() {
        LocalDate today = LocalDate.now();
        if (issueDate.isAfter(today) || expirationDate.isBefore(today)) {
            throw new IllegalStateException("사용 가능한 기간이 아닙니다.");
        }
    }
}
