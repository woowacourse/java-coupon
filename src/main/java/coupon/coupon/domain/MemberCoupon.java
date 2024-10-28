package coupon.coupon.domain;

import coupon.BaseEntity;
import coupon.member.domain.Member;
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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_coupon")
@Getter
@NoArgsConstructor
public class MemberCoupon extends BaseEntity {

    private static final int VALID_DAY = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "owner_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member owner;

    @JoinColumn(name = "coupon_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Coupon coupon;

    @Column(name = "used", nullable = false)
    private boolean used;

    @Column(name = "issued_at", nullable = false)
    private LocalDate issued_at;

    @Column(name = "expired_at", nullable = false)
    private LocalDate expiredAt;

    public MemberCoupon(Long id, Member owner, Coupon coupon) {
        this.id = id;
        this.owner = owner;
        this.coupon = coupon;
        this.used = false;
        this.issued_at = LocalDate.now();
        this.expiredAt = issued_at.plusDays(VALID_DAY + 1);
    }

    public MemberCoupon(final Member owner, final Coupon coupon) {
        this(null, owner, coupon);
    }

    public boolean isValid() {
        final var now = LocalDate.now();
        if (now.isBefore(expiredAt)) {
            return true;
        }
        return false;
    }
}
