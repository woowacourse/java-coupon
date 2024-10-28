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

    @JoinColumn(name = "owner", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member owner;

    @JoinColumn(name = "coupon", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Coupon coupon;

    public MemberCoupon(final Long id, final Member owner, final Coupon coupon) {
        this.id = id;
        this.owner = owner;
        this.coupon = coupon;
    }

    public MemberCoupon(final Member owner, final Coupon coupon) {
        this(null, owner, coupon);
    }

    public boolean isValid() {
        final var now = LocalDate.now();
        final var couponCreatedDate = getCreatedAt().toLocalDate();
        final var expirationTime = couponCreatedDate.plusDays(VALID_DAY);
        if (now.isAfter(expirationTime)) {
            return false;
        }
        return true;
    }
}
