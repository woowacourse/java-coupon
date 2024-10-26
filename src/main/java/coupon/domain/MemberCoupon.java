package coupon.domain;

import coupon.domain.coupon.Coupon;
import coupon.domain.member.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private boolean used;
    private LocalDate issueDate;
    private LocalDate expirationDate;

    public MemberCoupon(Coupon coupon, Member member, boolean used, LocalDate issueDate) {
        this.coupon = coupon;
        this.member = member;
        this.used = used;
        this.issueDate = issueDate;
        this.expirationDate = issueDate.plusDays(6);
    }

    public static MemberCoupon issue(Member member, Coupon coupon) {
        return new MemberCoupon(
                coupon,
                member,
                false,
                LocalDate.now()
        );
    }
}
