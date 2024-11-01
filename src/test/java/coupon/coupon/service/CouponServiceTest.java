package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.coupon.domain.Coupon;
import coupon.utils.IsolatedTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;

public class CouponServiceTest extends IsolatedTest {

    @Autowired
    private CouponService couponService;

    @DisplayName("reader DB에서 조회 시 복제 지연이 발생한다.")
    @Test
    void replicationLag() {
        Long couponId = couponService.createCoupon();
        assertThatThrownBy(() -> couponService.findCouponById(couponId))
                .isExactlyInstanceOf(InvalidDataAccessApiUsageException.class)
                .hasMessage("쿠폰이 존재하지 않습니다.");
    }

    @DisplayName("writer DB에서 바로 조회 시 복제 지연이 발생하지 않는다.")
    @Test
    void noReplicationLag() {
        Long couponId = couponService.createCoupon();
        Coupon savedCoupon = couponService.findCouponByIdWithNoLag(couponId);
        assertThat(savedCoupon).isNotNull();
    }
}
