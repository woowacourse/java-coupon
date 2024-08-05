package coupon.marketing.ui;

import coupon.marketing.domain.MonthlyMemberBenefit;
import coupon.marketing.service.MarketingService;
import java.time.Month;
import java.time.Year;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MarketingController {

    private final MarketingService marketingService;

    public MarketingController(MarketingService marketingService) {
        this.marketingService = marketingService;
    }

    @GetMapping("/marketing/max-coupon-discount-member")
    public MonthlyMemberBenefit findMaxCouponDiscountAmountMemberByMonth(@RequestParam("year") int year,
                                                                         @RequestParam("month") int month) {
        return marketingService.findMaxCouponDiscountAmountMemberByMonth(Year.of(year), Month.of(month));
    }
}
