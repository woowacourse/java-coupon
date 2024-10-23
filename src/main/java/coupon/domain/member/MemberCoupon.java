package coupon.domain.member;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import coupon.domain.coupon.Coupon;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MemberCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isUsed;

    private LocalDateTime createdAt;

    private LocalDateTime expiredAt;

    @ManyToOne
    private Member member;

    @OneToOne
    private Coupon coupon;

    public MemberCoupon(Boolean isUsed,
                        LocalDateTime createdAt,
                        LocalDateTime expiredAt,
                        Member member,
                        Coupon coupon) {
        this.isUsed = isUsed;
        validate(createdAt, expiredAt);
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.member = member;
        this.coupon = coupon;
    }

    private void validate(LocalDateTime createdAt, LocalDateTime expiredAt) {
        if (expiredAt.isBefore(createdAt)) {
            throw new IllegalArgumentException();
        }
    }
}
