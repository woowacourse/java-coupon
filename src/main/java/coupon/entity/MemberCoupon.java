package coupon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {

    private static final int COUPON_EXPIRATION_DAYS = 6;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "members_id")
    private Member member;

    @OneToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    private boolean used;

    private LocalDate issuedAt;

    private LocalDate expiredAt;

    @Builder
    public MemberCoupon(Member member, Coupon coupon) {
        this.member = member;
        this.coupon = coupon;
        this.issuedAt = LocalDate.now();
        this.expiredAt = issuedAt.plusDays(COUPON_EXPIRATION_DAYS);
    }
}
