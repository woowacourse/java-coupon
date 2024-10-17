package coupon.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
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
}
