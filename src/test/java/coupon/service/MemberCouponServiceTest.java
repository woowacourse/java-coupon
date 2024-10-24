package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import coupon.CouponException;
import coupon.dto.CouponCreateRequest;
import coupon.dto.CouponIssueRequest;
import coupon.dto.MemberCouponResponse;
import coupon.entity.Category;
import coupon.entity.Coupon;
import coupon.entity.MemberCoupon;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;

    @Autowired
    private CouponService couponService;

    @DisplayName("쿠폰을 발행한다. 발행한 쿠폰을 조회하는 경우, 쿠폰의 데이터도 함께 조회한다.")
    @Test
    void issue() {
        // given
        Coupon coupon = couponService.create(new CouponCreateRequest(
                "name",
                5000,
                50000,
                Category.FOOD,
                LocalDate.of(2024, 10, 11),
                LocalDate.of(2025, 10, 30)
        ));
        CouponIssueRequest couponIssueRequest = new CouponIssueRequest(coupon.getId(), 1L, LocalDate.of(2024, 10, 5));

        // when
        MemberCoupon issued = memberCouponService.issue(couponIssueRequest);

        // then
        MemberCouponResponse found = memberCouponService.read(issued.getId());
        assertAll(
                () -> assertThat(found.used()).isEqualTo(issued.isUsed()),
                () -> assertThat(found.start()).isEqualTo(issued.getStart()),
                () -> assertThat(found.end()).isEqualTo(issued.getEnd()),

                () -> assertThat(found.couponName()).isEqualTo(coupon.getName()),
                () -> assertThat(found.discount()).isEqualTo(coupon.getDiscount()),
                () -> assertThat(found.minimumOrder()).isEqualTo(coupon.getMinimumOrder()),
                () -> assertThat(found.category()).isEqualTo(coupon.getCategory()),
                () -> assertThat(found.couponStart()).isEqualTo(coupon.getStart()),
                () -> assertThat(found.couponEnd()).isEqualTo(coupon.getEnd())
        );
    }

    @DisplayName("해당 id의 발행한 쿠폰이 없는 경우 예외가 발생한다.")
    @Test
    void read_notFound() {
        assertThatThrownBy(() -> memberCouponService.read(-1L))
                .isInstanceOf(CouponException.class);
    }
}
