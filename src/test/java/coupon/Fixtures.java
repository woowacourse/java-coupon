package coupon;

import java.time.LocalDate;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.Member;

public final class Fixtures {

    public static final Coupon coupon = new Coupon(
            "쿠폰1",
            1000L,
            10000L,
            Category.FASHION,
            LocalDate.now(),
            LocalDate.now().plusDays(1L));

    public static final Member member = new Member("초롱");
}
