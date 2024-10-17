package coupon.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MemberCoupon {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Coupon coupon;

    private Boolean isUsed;

    @CreatedDate
    private LocalDateTime issuedAt;

    private LocalDateTime endedAt;

    public MemberCoupon(Member member, Coupon coupon, Boolean isUsed) {
        this.member = member;
        this.coupon = coupon;
        this.isUsed = isUsed;
        this.endedAt = issuedAt.plusDays(6);
    }
}
