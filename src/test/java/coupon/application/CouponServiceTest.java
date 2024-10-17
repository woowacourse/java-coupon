package coupon.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.List;
import coupon.domain.Coupon;
import coupon.domain.CouponCategory;
import coupon.domain.CouponIssuableDuration;
import coupon.domain.CouponName;
import coupon.domain.MemberCoupon;
import coupon.domain.MemberCouponRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("존재하지 않는 쿠폰은 발급할 수 없다.")
    void cantIssue() {
        long unknownCouponId = -1L;

        assertThatThrownBy(() -> couponService.issue(1L, unknownCouponId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("존재하지 않는 쿠폰입니다.");
    }

    @Test
    @DisplayName("사용자 쿠폰을 발급한다.")
    void issue() {
        couponService.issue(1L, 1L);
        entityManager.clear();

        List<MemberCoupon> result = memberCouponRepository.findAll();
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("쿠폰을 생성할 수 있다.")
    void create() {
        Coupon coupon = createCoupon();

        couponService.create(coupon);

        Coupon savedCoupon = couponService.getCoupon(coupon.getId());
        assertThat(savedCoupon).isNotNull();
    }

    private Coupon createCoupon() {
        LocalDate today = LocalDate.now();
        LocalDate end = today.plusDays(1);

        return new Coupon(
                new CouponName("1,000원 할인"),
                CouponCategory.FOOD,
                new CouponIssuableDuration(today, end),
                "1000",
                "10000"
        );
    }
}
