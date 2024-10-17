package coupon.membercoupon.domain;

import java.time.LocalDate;

public class MemberCoupon {

    private static final int USEABLE_DAYS = 7;

    private final boolean used;
    private final LocalDate publishedAt;
    private final LocalDate expiredAt;

    public MemberCoupon(boolean used, LocalDate publishedAt) {
        this.used = used;
        this.publishedAt = publishedAt;
        this.expiredAt = publishedAt.plusDays(USEABLE_DAYS);
    }
}
