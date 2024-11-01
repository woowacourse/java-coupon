package coupon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberCoupon {

    private static final int COUPON_EXPIRATION_DAYS = 6;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "members_id")
    private Member member;

    private long couponId;

    private boolean used;

    private LocalDate issuedAt;

    private LocalDate expiredAt;

    @Builder
    public MemberCoupon(Member member, long couponId) {
        this.member = member;
        this.couponId = couponId;
        this.used = false;
        this.issuedAt = LocalDate.now();
        this.expiredAt = issuedAt.plusDays(COUPON_EXPIRATION_DAYS);
    }
}
