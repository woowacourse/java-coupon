package coupon.domain;

import coupon.exception.CouponException;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberCoupon {

    private static final int EXPIRE_DAYS = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private boolean isUsed;

    private LocalDate issueDate;

    public MemberCoupon(Coupon coupon, Member member) {
        this.coupon = coupon;
        this.member = member;
        this.isUsed = false;
        this.issueDate = LocalDate.now();
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
