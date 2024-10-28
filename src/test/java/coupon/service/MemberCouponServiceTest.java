package coupon.service;

import static org.junit.jupiter.api.Assertions.*;

import coupon.data.Coupon;
import coupon.data.repository.CouponRepository;
import coupon.domain.coupon.CouponMapper;
import coupon.exception.MemberCouponIssueException;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberCouponServiceTest {

    private static final Coupon entity = new Coupon(
            "potato",
            1000,
            5000,
            "FOOD",
            LocalDateTime.of(2024, 11, 8, 9, 10),
            LocalDateTime.of(2024, 11, 8, 9, 10)
    );
    private static final coupon.domain.coupon.Coupon coupon = CouponMapper.fromEntity(entity);


    @Autowired
    private CouponService couponService;
    @Autowired
    private MemberCouponService memberCouponService;


    @DisplayName("동일 쿠폰을 5장을 초과하여 발행하려고 하면 예외가 발생한다.")
    @Test
    void issue() {
        couponService.create(coupon);
        for (int i = 0; i < 5; i++) {
            memberCouponService.issue(1, 1, LocalDateTime.now());
        }
        Assertions.assertThatThrownBy(() -> memberCouponService.issue(1, 1, LocalDateTime.now()))
                .isInstanceOf(MemberCouponIssueException.class)
                .hasMessageContaining("quantity max size exceeded");

    }

    @Test
    void findByMemberId() {
    }

    @Test
    void testIssue() {
    }

    @Test
    void testFindByMemberId() {
    }
}
