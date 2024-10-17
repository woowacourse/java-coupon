package coupon.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {

    private static final Validator validator;
    private static final int DEFAULT_AVAILABLE_DAYS = 7;

    static {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Coupon coupon;

    @NotNull
    @ManyToOne
    private Member member;

    private boolean used;

    private LocalDateTime issuedAt;

    private LocalDateTime expiresAt;

    public MemberCoupon(Coupon coupon, Member member) {
        this.coupon = coupon;
        this.member = member;
        this.used = false;
        this.issuedAt = LocalDateTime.now();
        this.expiresAt = calculateExpiresAt(this.issuedAt);

        validate();
    }

    private LocalDateTime calculateExpiresAt(LocalDateTime issuedAt) {
        return issuedAt.toLocalDate()
                .plusDays(DEFAULT_AVAILABLE_DAYS)
                .atStartOfDay()
                .minusNanos(1);
    }

    @AssertTrue(message = "발급일은 만료일보다 이전이어야 합니다.")
    public boolean validateIssuedAt() {
        return issuedAt.isBefore(expiresAt);
    }

    private void validate() {
        Set<ConstraintViolation<MemberCoupon>> violations = validator.validate(this);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
