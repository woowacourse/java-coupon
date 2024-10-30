package coupon.service;

import coupon.config.CouponCache;
import coupon.data.CouponEntity;
import coupon.domain.coupon.CouponMapper;
import coupon.exception.MemberCouponIssueException;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberCouponEntityEntityServiceTest {

    private static final CouponEntity entity = new CouponEntity(
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

    @BeforeEach
    void before(){

    }

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

    @DisplayName("쿠폰 정보가 캐싱 되었는지 확인한다.")
    @Test
    void findByMemberId() {
        CouponEntity newCouponEntity = couponService.create(coupon);
        memberCouponService.issue(1, newCouponEntity.getId(), LocalDateTime.now());
        memberCouponService.findByMemberId(1);

        CouponEntity cachedCouponEntity = CouponCache.get(newCouponEntity.getId());
        Assertions.assertThat(cachedCouponEntity).isEqualTo(newCouponEntity);
    }

}
