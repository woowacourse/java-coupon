package coupon.coupon.domain;

import coupon.member.domain.Member;
import coupon.util.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_coupon")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(optional = false)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Column(nullable = false)
    private boolean isUsed = false;

    @Column(name = "issue_started_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime issueStartedAt;

    @Column(name = "issue_ended_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime issueEndedAt;

    public MemberCoupon(Member member, Coupon coupon) {
        this.member = member;
        this.coupon = coupon;
        this.isUsed = false;
        this.issueStartedAt = LocalDateTime.now();
        this.issueEndedAt = calculateIssueEndDate(this.issueStartedAt);
    }

    private LocalDateTime calculateIssueEndDate(LocalDateTime issueStartedAt) {
        return issueStartedAt.plusDays(7).withHour(23).withMinute(59).withSecond(59).withNano(999_999);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MemberCoupon that = (MemberCoupon) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MemberCoupon{" +
                "id=" + id +
                ", member=" + member +
                ", coupon=" + coupon +
                ", isUsed=" + isUsed +
                ", issueStartedAt=" + issueStartedAt +
                ", issueEndedAt=" + issueEndedAt +
                '}';
    }
}
