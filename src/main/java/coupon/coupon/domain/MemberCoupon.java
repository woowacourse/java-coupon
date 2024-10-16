package coupon.coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class MemberCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private boolean used;

    @Column(nullable = false)
    private LocalDateTime issuedAt;

    @Column(nullable = false)
    private LocalDateTime expireAt;

    public MemberCoupon(Coupon coupon, Long memberId) {
        this.coupon = coupon;
        this.memberId = memberId;
        this.used = false;
        this.issuedAt = LocalDateTime.now();
        this.expireAt = calculateExpireAt(this.issuedAt);
    }

    private LocalDateTime calculateExpireAt(LocalDateTime issuedAt) {
        return issuedAt.plusDays(7).with(LocalTime.of(23, 59, 59, 999_999_000));
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
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
