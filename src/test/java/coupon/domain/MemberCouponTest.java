package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import coupon.exception.CouponException;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    private MemberCoupon memberCoupon;

    @BeforeEach
    void setUp() {
        Member member = new Member("teni");

        LocalDate since = LocalDate.now().minusDays(2);
        LocalDate until = LocalDate.now().plusDays(100);
        Coupon coupon = new Coupon("5,000원 할인쿠폰", 5000, 50000, since, until, Category.FOOD);

        memberCoupon = new MemberCoupon(coupon, member);
    }

    @DisplayName("성공: 쿠폰 사용")
    @Test
    void use() {
        memberCoupon.use();
        assertThat(memberCoupon.isUsed()).isTrue();
    }

    @DisplayName("실패: 이미 사용된 쿠폰을 사용할 수 없음")
    @Test
    void use_Fail_AlreadyUsed() {
        memberCoupon.use();
        assertThatThrownBy(() -> memberCoupon.use())
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("만료 날짜는 발급일로부터 7일 뒤이다.")
    @Test
    void checkExpireDate() {
        assertThat(memberCoupon.checkExpireDate()).isEqualTo(LocalDate.now().plusDays(7));
    }

    @DisplayName("사용되지 않았고 만료되지 않은 쿠폰은 사용 가능하다.")
    @Test
    void isUsable() {
        assertThat(memberCoupon.isUsable()).isTrue();
    }

    @DisplayName("만료된 쿠폰은 사용 불가능하다.")
    @Test
    void isUsable_False() {
        MemberCoupon mockMemberCoupon = mock(MemberCoupon.class);

        doReturn(LocalDate.now())
                .when(mockMemberCoupon).checkExpireDate();

        assertThat(memberCoupon.isUsable()).isTrue();
    }
}
