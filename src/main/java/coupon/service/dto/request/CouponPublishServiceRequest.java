package coupon.service.dto.request;

import java.time.LocalDateTime;

public record CouponPublishServiceRequest(OrderCreateServiceRequest orderRequest,
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

    public long orderPrice() {
        return orderRequest.price();
    }

    public String orderCategory() {
        return orderRequest.category();
    }
}
