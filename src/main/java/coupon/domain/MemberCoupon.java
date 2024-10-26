package coupon.domain;

import coupon.domain.coupon.Coupon;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    private boolean isUsed;
    private LocalDate issueDate;
    private LocalDate expirationDate;

    private MemberCoupon(Long id, Coupon coupon, Member member, boolean isUsed, LocalDate issueDate,
                         LocalDate expirationDate) {
        this.id = id;
        this.coupon = coupon;
        this.member = member;
        this.isUsed = isUsed;
        this.issueDate = issueDate;
        this.expirationDate = expirationDate;
    }

    public static MemberCoupon issue(Coupon coupon, Member member) {
        LocalDate today = LocalDate.now();
        validateCouponCanIssue(coupon, today);
        return new MemberCoupon(null, coupon, member, false, today, today.plusDays(6));
    }

    private static void validateCouponCanIssue(Coupon coupon, LocalDate date) {
        if (coupon.canIssueAt(date)) {
            return;
        }
        throw new IllegalStateException("쿠폰 발급 가능 기간이 아닙니다.");
    }
}
