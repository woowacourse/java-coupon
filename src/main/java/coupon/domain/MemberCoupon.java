package coupon.domain;

import coupon.exception.CouponException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberCoupon {

    private static final int EXPIRE_DAYS = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Column(name = "is_used")
    private boolean isUsed;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    public MemberCoupon(Member member, Coupon coupon) {
        LocalDateTime now = LocalDateTime.now();
        validate(now, coupon);
        this.member = member;
        this.coupon = coupon;
        this.isUsed = false;
        this.issueDate = now.toLocalDate();
    }

    private void validate(LocalDateTime now, Coupon coupon) {
        if (!coupon.isIssuableAt(now)) {
            throw new CouponException("쿠폰 발급 기간이 아닙니다.");
        }
    }

    public void use() {
        if (isUsed) {
            throw new CouponException("이미 사용된 쿠폰입니다.");
        }

        isUsed = true;
    }

    public LocalDate checkExpireDate() {
        return issueDate.plusDays(EXPIRE_DAYS);
    }

    public boolean isUsable() {
        return !isUsed && LocalDate.now().isBefore(checkExpireDate());
    }
}
