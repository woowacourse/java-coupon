package coupon.domain;

import member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberCouponTest {

    private Coupon coupon;
    private Member member;
    private boolean isUsed;
    private LocalDateTime grantDateTime;

    @BeforeEach
    void setUp() {
        coupon = new Coupon("coupon_test", 3000, 100000, Category.FASHION,
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31));
        member = new Member(1L, "test", "password");
        isUsed = false;
        grantDateTime = LocalDateTime.of(2024, 5, 5, 12, 30, 30);
    }

    @Test
    @DisplayName("만료 시간을 확인할 수 있다.")
    void getExpireDateTime() {
        //given
        LocalDateTime expectedExpireTime = LocalDateTime.of(2024, 5, 11, 23, 59, 59, 999999000);

        //when
        MemberCoupon memberCoupon = new MemberCoupon(coupon.getId(), member.getId(), isUsed, grantDateTime);

        //then
        assertThat(memberCoupon.getExpireDateTime()).isEqualTo(expectedExpireTime);
    }

    @CsvSource({"2024-05-07T12:30:30, false, true",
            "2024-05-07T12:30:30, true, false",
            "2024-05-07T23:59:59, false, true",
            "2024-05-11T23:59:59.999999, false, true",
            "2024-05-11T23:59:59.9999999, false, false",})
    @ParameterizedTest
    @DisplayName("발급일 포함 7일 동안, 만료일의 23:59:59.999999까지 쿠폰 사용 가능하다.")
    void isUsable(LocalDateTime requestTime, boolean isUsed, boolean expected) {
        //given
        MemberCoupon memberCoupon = new MemberCoupon(coupon.getId(), member.getId(), isUsed, grantDateTime);

        //when
        boolean result = memberCoupon.isUsable(requestTime);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("쿠폰을 사용할 수 있다.")
    void use() {
        //given
        MemberCoupon memberCoupon = new MemberCoupon(coupon.getId(), member.getId(), isUsed, grantDateTime);
        LocalDateTime usableDateTime = grantDateTime.with(LocalTime.of(23, 59, 59));
        assertThat(memberCoupon.isUsable(usableDateTime)).isTrue();

        //when
        memberCoupon.use(member.getId());

        //then
        assertThat(memberCoupon.isUsable(usableDateTime)).isFalse();
    }

    @Test
    @DisplayName("쿠폰 소유자가 아닌 사람은 쿠폰을 사용할 수 없다.")
    void use_withInvalidUser() {
        //given
        MemberCoupon memberCoupon = new MemberCoupon(coupon.getId(), member.getId(), isUsed, grantDateTime);

        //when, then
        assertThrows(IllegalArgumentException.class, () -> memberCoupon.use(2L));
    }
}
