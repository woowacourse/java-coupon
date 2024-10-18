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
