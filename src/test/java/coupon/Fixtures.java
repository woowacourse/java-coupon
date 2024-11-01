package coupon;

import java.time.LocalDate;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.Member;

public final class Fixtures {

    public static final Coupon coupon_1 = new Coupon(
            "쿠폰1",
            1000L,
            10000L,
            Category.FASHION,
            LocalDate.now(),
            LocalDate.now().plusDays(1L));

    public static final Coupon coupon_2 = new Coupon(
            "쿠폰2",
            2000L,
            20000L,
            Category.APPLIANCE,
            LocalDate.now(),
            LocalDate.now().plusDays(2L));

    public static final Member member_1 = new Member("초롱");

    public static final Member member_2 = new Member("몰리");
}
