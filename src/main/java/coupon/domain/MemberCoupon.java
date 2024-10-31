package coupon.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class MemberCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long couponId;

    @ManyToOne
    private Member member;

    private boolean used;

    private LocalDateTime issuedAt;

    private LocalDateTime expiredAt;

    public MemberCoupon(Long couponId, Member member) {
        this.couponId = couponId;
        this.member = member;
        this.used = false;
        this.issuedAt = LocalDateTime.now();
        this.expiredAt = issuedAt.plusDays(6).withHour(23).withMinute(59).withSecond(59).withNano(999999000);
    }
}


