package coupon.membercoupon.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

public class MemberCoupon {

    private static final int USEABLE_DAYS = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long couponId;
    private Long memberId;
    private final boolean used;
    private final LocalDate publishedAt;
    private final LocalDate expiredAt;

    public MemberCoupon(boolean used, LocalDate publishedAt) {
        this.used = used;
        this.publishedAt = publishedAt;
        this.expiredAt = publishedAt.plusDays(USEABLE_DAYS);
    }
}
