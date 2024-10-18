package coupon.service;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import coupon.domain.Category;
import coupon.domain.repository.CouponRepository;
import coupon.service.dto.request.CouponCreateServiceRequest;
import coupon.service.dto.request.CouponPublishServiceRequest;
import coupon.service.dto.request.OrderCreateServiceRequest;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService sut;

    @Autowired
    private CouponRepository couponRepository;

    @Test
    @Disabled
    @DisplayName("쿠폰을 발급한다.")
    void publish_coupon() {
        // given
        final long orderPrice = 10000L;
        final long discountAmount = 1000L;
        final CouponPublishServiceRequest request = publishCouponServiceRequest(orderPrice, discountAmount);

        // when
        final long id = sut.publish(request);

        // then
    }

    private CouponPublishServiceRequest publishCouponServiceRequest(final long price, final long discountAmount) {
        final OrderCreateServiceRequest orderRequest = new OrderCreateServiceRequest(
                price, Category.FASHION.name());
        final CouponCreateServiceRequest couponRequest = new CouponCreateServiceRequest(
                "couponName",
                discountAmount,
                LocalDateTime.of(2024, 1, 1, 1, 0),
                LocalDateTime.of(2024, 1, 1, 1, 1)
        );
        return new CouponPublishServiceRequest(orderRequest, couponRequest);
    }
}
