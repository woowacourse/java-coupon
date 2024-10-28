package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class MemberCoupon {
    private static long EXPIRATION_DAY = 7L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "coupon_id")
    private long couponId;

    @Column(nullable = false, name = "member_id")
    private long memberId;

    @Column(nullable = false, name = "is_used")
    private boolean isUsed;

    @Column(nullable = false, name = "issue_date")
    private LocalDate issueDate;

    protected MemberCoupon() {
    }

    public MemberCoupon(long couponId, long memberId, LocalDate issueDate) {
        validate(issueDate);
        this.couponId = couponId;
        this.memberId = memberId;
        this.issueDate = issueDate;
        this.isUsed = false;
    }

    public MemberCoupon(long couponId, long memberId) {
        this(couponId, memberId, LocalDate.now());
    }

    private void validate(LocalDate issueDate) {
        validateIssueDate(issueDate);
    }

    private void validateMember(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("사용자는 필수입니다.");
        }
    }

    private void validateIssueDate(LocalDate issueDate) {
        if (issueDate == null) {
            throw new IllegalArgumentException("발급일은 필수입니다.");
        }
    }

    public void use() {
        validateExpiration();
        isUsed = true;
    }

    private void validateExpiration() {
        if (issueDate.plusDays(EXPIRATION_DAY).isBefore(LocalDate.now())) {
            throw new IllegalStateException(
                    String.format("쿠폰의 사용 기한이 만료되었습니다. 쿠폰은 발급일로부터 %d일 동안 사용 가능합니다.", EXPIRATION_DAY));
        }
    }

    public boolean isUsed() {
        return isUsed;
    }

    public Long getId() {
        return id;
    }

    public long getCouponId() {
        return couponId;
    }

    public long getMemberId() {
        return memberId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }
}
