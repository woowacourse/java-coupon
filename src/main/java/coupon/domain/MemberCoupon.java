package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private static final int COUPON_EXPIRATION_DAYS = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long couponId;

    @Column(nullable = false)
    private boolean isUsed;

    @Column(nullable = false)
    private LocalDateTime issueStartDate;

    @Column(nullable = false)
    private LocalDateTime issueEndDate;

    public MemberCoupon(Long memberId, Long couponId, boolean isUsed, LocalDateTime issueStartDate) {
        this.memberId = memberId;
        this.couponId = couponId;
        this.isUsed = isUsed;
        this.issueStartDate = issueStartDate;
        this.issueEndDate = getIssueEndDate(issueStartDate);
        validate();
    }

    public static MemberCoupon issue(Long memberId, Long couponId, LocalDateTime issueStartDate) {
        return new MemberCoupon(memberId, couponId, false, issueStartDate);
    }

    private LocalDateTime getIssueEndDate(LocalDateTime issueStartDate) {
        return issueStartDate.plusDays(COUPON_EXPIRATION_DAYS)
                .with(LocalTime.of(23, 59, 59, 999_999_000));
    }
    private void validate() {
        Set<ConstraintViolation<MemberCoupon>> violations = validator.validate(this);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<MemberCoupon> violation : violations) {
                sb.append(violation.getMessage()).append("\n");
            }
            throw new IllegalArgumentException(sb.toString());
        }
    }

}
