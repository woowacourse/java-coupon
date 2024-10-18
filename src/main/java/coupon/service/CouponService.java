package coupon.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.CouponName;
import coupon.domain.Discount;
import coupon.domain.DiscountRateCalculator;
import coupon.domain.Order;
import coupon.domain.Period;
import coupon.domain.repository.CouponRepository;
import coupon.domain.repository.OrderRepository;
import coupon.service.dto.request.CouponPublishServiceRequest;
import coupon.service.dto.response.CouponServiceResponse;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponService {

    private final OrderRepository orderRepository;
    private final CouponRepository couponRepository;

    @Transactional
    public long publish(final CouponPublishServiceRequest request) {
        validateDiscountRate(request);
        final Order order = createOrder(request);
        final Coupon coupon = createCoupon(request, order);
        orderRepository.save(order);
        couponRepository.save(coupon);
        return coupon.getId();
    }

    @Transactional
    public CouponServiceResponse read(final long couponId) {
        final Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 쿠폰 ID입니다."));
        return new CouponServiceResponse(coupon.getCouponName(),
                coupon.getDiscountAmount(),
                coupon.getStartAt(),
                coupon.getEndAt());
    }

    private void validateDiscountRate(final CouponPublishServiceRequest request) {
        final DiscountRateCalculator calculator = new DiscountRateCalculator(request.orderPrice(),
                request.discountAmount());
        calculator.validateRate();
    }

    private Order createOrder(final CouponPublishServiceRequest request) {
        final long price = request.orderPrice();
        final Category category = Category.from(request.orderCategory());
        return new Order(price, category);
    }

    private Coupon createCoupon(final CouponPublishServiceRequest request, final Order order) {
        final CouponName couponName = new CouponName(request.couponName());
        final Discount discount = new Discount(request.discountAmount());
        final Period period = new Period(request.couponStart(), request.couponEnd());
        return new Coupon(couponName, discount, period, order);
    }
}
