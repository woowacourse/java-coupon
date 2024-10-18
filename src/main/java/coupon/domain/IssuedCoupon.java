package coupon.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class IssuedCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Coupon coupon;

    @ManyToOne
    private Member member;

    private Boolean isUsed;

    private LocalDate createdAt;

    private LocalDate expiredAt;

    public static IssuedCoupon issue(Coupon coupon, Member member) {
        return new IssuedCoupon(null, coupon, member, false, LocalDate.now(), LocalDate.now().plusDays(7));
    }
}
