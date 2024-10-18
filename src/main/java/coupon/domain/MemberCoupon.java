package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class MemberCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false, name = "is_used")
    private boolean isUsed;

    @Column(nullable = false, name = "issue_date")
    private LocalDate issueDate;

    protected MemberCoupon() {
    }

    public MemberCoupon(Coupon coupon, Member member, LocalDate issueDate) {
        validate(coupon, member, issueDate);
        this.coupon = coupon;
        this.member = member;
        this.issueDate = issueDate;
        this.isUsed = false;
    }

    private void validate(Coupon coupon, Member member, LocalDate issueDate) {
        validateCoupon(coupon);
        validateMember(member);
        validateIssueDate(issueDate);
    }

    private void validateCoupon(Coupon coupon) {
        if (coupon == null) {
            throw new IllegalArgumentException("쿠폰은 필수입니다.");
        }
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
}
