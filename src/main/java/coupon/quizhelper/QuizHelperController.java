package coupon.quizhelper;

import coupon.coupon.repository.CouponRepository;
import coupon.coupon.repository.MemberCouponRepository;
import coupon.quizhelper.dto.InitializeUsedRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuizHelperController {

    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;

    public QuizHelperController(CouponRepository couponRepository, MemberCouponRepository memberCouponRepository) {
        this.couponRepository = couponRepository;
        this.memberCouponRepository = memberCouponRepository;
    }

    @PutMapping("/coupons/initialize-issue-count/{couponId}")
    public void initializeIssueCount(@PathVariable("couponId") Long couponId) {
        couponRepository.updateIssueCount(couponId, 0L);
    }

    @PutMapping("/coupons/initialize-use-count/{couponId}")
    public void initializeUseCount(@PathVariable("couponId") Long couponId) {
        couponRepository.updateUseCount(couponId, 0L);
    }

    @PutMapping("/member-coupons/initialize-used")
    public void initializeUseCount(@RequestBody InitializeUsedRequest initializeUsedRequest) {
        memberCouponRepository.updateUsedAndUsedAt(initializeUsedRequest.memberCouponIds(), false, null);
    }

}
