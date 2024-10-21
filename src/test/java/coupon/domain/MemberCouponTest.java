package coupon.domain;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.vo.DiscountAmount;
import coupon.domain.vo.IssuePeriod;
import coupon.domain.vo.MinimumOrderPrice;
import coupon.domain.vo.Name;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberCouponTest {

    private Coupon coupon;
    private Member member;

    @BeforeEach
    void setUp() {
        Name name = new Name("쿠폰이름");
        DiscountAmount discountAmount = new DiscountAmount(1_000);
        MinimumOrderPrice minimumOrderPrice = new MinimumOrderPrice(30_000);
        IssuePeriod issuePeriod = new IssuePeriod(LocalDateTime.now(), LocalDateTime.now());

        this.coupon = new Coupon(name, discountAmount, minimumOrderPrice, Category.FASHION, issuePeriod);
        this.member = new Member("미아");
    }

    @Test
    @DisplayName("회원에게 발급된 쿠폰은 발급일 포함 7일 동안 사용 가능하다.")
    void createUseEndedAt() {
        MemberCoupon memberCoupon = new MemberCoupon(coupon, member, false);
        LocalDateTime useTime = LocalDateTime.now().plusDays(7);

        boolean isAvailable = memberCoupon.isAvailable(useTime);

        assertThat(isAvailable).isTrue();
    }

}
