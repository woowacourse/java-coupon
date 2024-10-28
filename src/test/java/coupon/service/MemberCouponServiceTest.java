package coupon.service;

import static org.junit.jupiter.api.Assertions.*;

import coupon.data.Coupon;
import coupon.data.repository.CouponRepository;
import coupon.domain.coupon.CouponMapper;
import coupon.exception.MemberCouponIssueException;
import java.time.LocalDateTime;
import java.util.List;
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

    @DisplayName("회원의 쿠폰 목록 조회 시 쿠폰, 회원에게 발급된 쿠폰의 정보를 보여준다.")
    @Test
    void findByMemberId() {
        Coupon coupon1 = couponService.create(coupon);
        System.out.println(coupon1);
        for (int i = 0; i < 5; i++) {
            memberCouponService.issue(1, 1, LocalDateTime.now());
        }
        List<CouponResponse> byMemberId = memberCouponService.findByMemberId(1);

        System.out.println(byMemberId.get(0).coupon() + " " + byMemberId.get(0).memberCoupon());
    }

    @Test
    void testIssue() {
    }

    @Test
    void testFindByMemberId() {
    }
}
