package coupon.domain.coupon;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CouponIssueDateTest {

    @Test
    void 오늘이_쿠폰_발급일_이상_만료일_이하의_날짜라면_쿠폰_사용이_가능하다() {
        // given
        LocalDate now = LocalDate.now();
        LocalDate issueStartDate = now.minusDays(1);
        LocalDate issueEndDate = now.plusDays(1);
        CouponIssueDate couponIssueDate = new CouponIssueDate(issueStartDate, issueEndDate);

        //when
        boolean dateAvailable1 = couponIssueDate.isDateAvailable(now);
        boolean dateAvailable2 = couponIssueDate.isDateAvailable(issueStartDate);

        //then
        assertAll(
                () -> Assertions.assertThat(dateAvailable1).isTrue(),
                () -> Assertions.assertThat(dateAvailable2).isTrue()
        );
    }

    @Test
    void 오늘이_쿠폰_발급일_미만_만료일_초과의_날짜라면_쿠폰_사용이_불가능하다() {
        // given
        LocalDate now = LocalDate.now();
        LocalDate issueStartDate = now.plusDays(1);
        LocalDate issueEndDate = now.plusDays(2);
        CouponIssueDate couponIssueDate = new CouponIssueDate(issueStartDate, issueEndDate);

        //when
        boolean dateAvailable = couponIssueDate.isDateAvailable(now);

        //then
        Assertions.assertThat(dateAvailable).isFalse();
    }
}
