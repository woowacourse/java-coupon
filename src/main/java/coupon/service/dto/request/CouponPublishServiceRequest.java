package coupon.service.dto.request;

import java.time.LocalDateTime;

public record CouponPublishServiceRequest(PaymentCreateServiceRequest paymentRequest,
                                          CouponCreateServiceRequest couponRequest) {

    public String couponName() {
        return couponRequest.couponName();
    }

    public long discountAmount() {
        return couponRequest.discountAmount();
    }

    public LocalDateTime couponStart() {
        return couponRequest.start();
    }

    public LocalDateTime couponEnd() {
        return couponRequest.end();
    }

    public long paymentPrice() {
        return paymentRequest.price();
    }

    public String paymentCategory() {
        return paymentRequest.category();
    }
}
