package coupon.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

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

    private LocalDateTime issuedAt;

    private LocalDateTime endedAt = issuedAt.plusDays(6);
}
