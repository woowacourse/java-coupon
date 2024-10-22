package coupon.membercoupon.ui;

import java.util.List;
import coupon.membercoupon.application.MemberCouponService;
import coupon.membercoupon.application.MemberCouponWithCouponResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberCouponController {

    private final MemberCouponService memberCouponService;

    @GetMapping("/member-coupons/{memberId}")
    public ResponseEntity<List<MemberCouponWithCouponResponse>> getMemberCouponWithCoupons(@PathVariable Long memberId) {
        List<MemberCouponWithCouponResponse> responses = memberCouponService.getMemberCouponWithCoupons(memberId);

        return ResponseEntity.ok(responses);
    }
}
