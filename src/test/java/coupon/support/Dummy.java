package coupon.support;

import java.time.LocalDateTime;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponName;
import coupon.domain.coupon.Discount;
import coupon.domain.coupon.Period;
import coupon.domain.member.Member;
import coupon.domain.payment.Category;
import coupon.domain.payment.Payment;

public abstract class Dummy {

    public static Coupon createCoupon(final String couponName) {
        final CouponName normalCoupon = new CouponName(couponName);
        final Discount discount = new Discount(5000L);
        final LocalDateTime startAt = LocalDateTime.of(2024, 03, 01, 01, 01);
        final LocalDateTime endAt = LocalDateTime.of(2024, 03, 01, 02, 02);
        final Period period = new Period(startAt, endAt);
        final Payment payment = new Payment(6000L, Category.FASHION);
        return new Coupon(normalCoupon, discount, period, payment);
    }

    public static Coupon createCoupon(final String couponName, final Payment payment) {
        final CouponName normalCoupon = new CouponName(couponName);
        final Discount discount = new Discount(5000L);
        final LocalDateTime startAt = LocalDateTime.of(2024, 03, 01, 01, 01);
        final LocalDateTime endAt = LocalDateTime.of(2024, 03, 01, 02, 02);
        final Period period = new Period(startAt, endAt);
        return new Coupon(normalCoupon, discount, period, payment);
    }

    public static Payment createPayment(final long price, final Category category) {
        return new Payment(price, category);
    }

    public static Member createMember(final String userName) {
        return new Member(userName);
    }
}
