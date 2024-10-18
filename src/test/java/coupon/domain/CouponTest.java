package coupon.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CouponTest {

    @DisplayName("쿠폰이 정상적으로 생성된다.")
    @Test
    void createCouponSuccessfully() {
        String couponName = "NakNak Coupon";
        int discountAmount = 1000;
        int minimumOrderAmount = 5000;
        CouponCategory couponCategory = CouponCategory.FASHION;
        LocalDateTime issueStartedAt = LocalDateTime.now();
        LocalDateTime issueEndedAt = issueStartedAt.plusDays(3);
        long issueLimit = 100;
        long issueCount = 0;

        assertThatNoException()
                .isThrownBy(() -> new Coupon(
                        couponName,
                        discountAmount, minimumOrderAmount,
                        couponCategory,
                        issueStartedAt, issueEndedAt,
                        issueLimit, issueCount)
                );
    }

    @DisplayName("할인율이 3% 미만일 경우 예외를 발생시킨다.")
    @Test
    void throwsWhenDiscountRateIsBelowMin() {
        String couponName = "Kyummi Coupon";
        int discountAmount = 1000;
        int minimumOrderAmount = 35000;
        CouponCategory couponCategory = CouponCategory.FOOD;
        LocalDateTime issueStartedAt = LocalDateTime.now();
        LocalDateTime issueEndedAt = issueStartedAt.plusDays(3);
        long issueLimit = 100;
        long issueCount = 0;

        assertThatThrownBy(
                () -> new Coupon(
                        couponName,
                        discountAmount, minimumOrderAmount,
                        couponCategory,
                        issueStartedAt, issueEndedAt,
                        issueLimit, issueCount
                ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 3% 이상 20% 이하여야 합니다.");
    }

    @DisplayName("할인율이 20% 초과할 경우 예외를 발생시킨다.")
    @Test
    void throwsWhenDiscountRateExceedsMax() {
        String couponName = "ALPACA Coupon";
        int discountAmount = 3000;
        int minimumOrderAmount = 10000;
        CouponCategory couponCategory = CouponCategory.FASHION;
        LocalDateTime issueStartedAt = LocalDateTime.now();
        LocalDateTime issueEndedAt = issueStartedAt.plusDays(3);
        long issueLimit = 100;
        long issueCount = 0;

        assertThatThrownBy(
                () -> new Coupon(
                        couponName,
                        discountAmount, minimumOrderAmount,
                        couponCategory,
                        issueStartedAt, issueEndedAt,
                        issueLimit, issueCount
                ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("할인율은 3% 이상 20% 이하여야 합니다.");
    }

    @DisplayName("쿠폰 발급이 정상적으로 이루어진다.")
    @Test
    void issueCouponSuccessfully() {
        CouponName couponName = new CouponName("Jazz Coupon");
        DiscountAmount discountAmount = new DiscountAmount(1000);
        MinimumOrderAmount minimumOrderAmount = new MinimumOrderAmount(5000);
        CouponCategory couponCategory = CouponCategory.FASHION;
        CouponPeriod couponPeriod = new CouponPeriod(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
        long issueLimit = 100;
        long issueCount = 0;

        Coupon coupon = new Coupon(couponName, discountAmount, minimumOrderAmount, couponCategory, couponPeriod,
                issueLimit, issueCount);

        assertThatNoException()
                .isThrownBy(coupon::issue);
    }

    @DisplayName("쿠폰 발급이 불가능한 시간일 경우 예외를 발생시킨다.")
    @Test
    void throwsWhenCouponIsNotIssuable() {
        CouponName couponName = new CouponName("Expired Coupon");
        DiscountAmount discountAmount = new DiscountAmount(1000);
        MinimumOrderAmount minimumOrderAmount = new MinimumOrderAmount(5000);
        CouponCategory couponCategory = CouponCategory.FOOD;
        CouponPeriod couponPeriod = new CouponPeriod(LocalDateTime.now().minusDays(1),
                LocalDateTime.now().minusDays(1));
        long issueLimit = 100;
        long issueCount = 90;

        Coupon coupon = new Coupon(couponName, discountAmount, minimumOrderAmount, couponCategory, couponPeriod,
                issueLimit, issueCount);

        assertThatThrownBy(coupon::issue)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰을 발급할 수 없는 시간입니다.");
    }

    @DisplayName("발급 한도를 초과하면 예외를 발생시킨다.")
    @Test
    void throwsWhenIssueCountExceedsLimit() {
        CouponName couponName = new CouponName("Limited Coupon");
        DiscountAmount discountAmount = new DiscountAmount(1000);
        MinimumOrderAmount minimumOrderAmount = new MinimumOrderAmount(5000);
        CouponCategory couponCategory = CouponCategory.FOOD;
        CouponPeriod couponPeriod = new CouponPeriod(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
        long issueLimit = 100;
        long issueCount = 100;

        Coupon coupon = new Coupon(couponName, discountAmount, minimumOrderAmount, couponCategory, couponPeriod,
                issueLimit, issueCount);

        assertThatThrownBy(coupon::issue)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰을 더 이상 발급할 수 없습니다.");
    }
}
