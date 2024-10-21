package coupon.membercoupon.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class MemberCoupon {

    private static final int USEABLE_DAYS = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long couponId;
    private Long memberId;
    private boolean used;
    private LocalDate publishedAt;
    private LocalDate expiredAt;

    protected MemberCoupon() {

    }

    public MemberCoupon(boolean used, LocalDate publishedAt) {
        this.used = used;
        this.publishedAt = publishedAt;
        this.expiredAt = publishedAt.plusDays(USEABLE_DAYS);
    }
}
