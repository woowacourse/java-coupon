package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import coupon.domain.Category;
import coupon.domain.repository.CouponRepository;
import coupon.service.dto.request.CouponCreateServiceRequest;
import coupon.service.dto.request.CouponPublishServiceRequest;
import coupon.service.dto.request.OrderCreateServiceRequest;
import coupon.service.dto.response.CouponServiceResponse;

@SpringBootTest
class CouponServiceTest {

    @Autowired
    private CouponService sut;

    @Autowired
    private CouponRepository couponRepository;

    @Test
    @DisplayName("쿠폰을 발급한다.")
    void publish_coupon() {
        // given
        final long orderPrice = 10000L;
        final long discountAmount = 1000L;
        final LocalDateTime startAt = LocalDateTime.of(2024, 1, 1, 1, 0);
        final LocalDateTime endAt = LocalDateTime.of(2024, 1, 1, 1, 1);

        final CouponPublishServiceRequest request = publishCouponServiceRequest(orderPrice, discountAmount, startAt,
                endAt);

        // when
        final long id = sut.publish(request);

        // then
        final CouponServiceResponse expect = new CouponServiceResponse("couponName", discountAmount, startAt,
                endAt);
        assertThat(sut.read(id))
                .isEqualTo(expect);
    }

    private CouponPublishServiceRequest publishCouponServiceRequest(final long price,
                                                                    final long discountAmount,
                                                                    final LocalDateTime start,
                                                                    final LocalDateTime endAt) {
        final OrderCreateServiceRequest orderRequest = new OrderCreateServiceRequest(
                price, Category.FASHION.name());
        final CouponCreateServiceRequest couponRequest = new CouponCreateServiceRequest(
                "couponName",
                discountAmount,
                start,
                endAt
        );
        return new CouponPublishServiceRequest(orderRequest, couponRequest);
    }
}
