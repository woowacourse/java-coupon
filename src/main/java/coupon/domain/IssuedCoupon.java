package coupon.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class IssuedCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    private Long couponId;

    private Boolean isUsed;

    private LocalDate createdAt;

    private LocalDate expiredAt;

    public IssuedCoupon(Member member, Long couponId, Boolean isUsed, LocalDate createdAt, LocalDate expiredAt) {
        this.member = member;
        this.couponId = couponId;
        this.isUsed = isUsed;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
    }

    public static IssuedCoupon issue(Coupon coupon, Member member) {
        return new IssuedCoupon(member, coupon.getId(), false, LocalDate.now(), LocalDate.now().plusDays(7));
    }
}
