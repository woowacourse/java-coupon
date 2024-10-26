package coupon;

import coupon.coupon.business.CouponService;
import coupon.coupon.business.MemberCouponService;
import coupon.coupon.common.Fixture;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.IssuePeriod;
import coupon.coupon.domain.MemberCoupon;
import coupon.coupon.dto.MemberCouponResponse;
import coupon.coupon.persistence.CouponWriter;
import coupon.member.domain.Member;
import coupon.member.persistence.MemberWriter;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CouponApplicationTests {

    @Autowired
    private MemberCouponService memberCouponService;

    @Autowired
    private CouponWriter couponWriter;

    @Autowired
    private MemberWriter memberWriter;

    @Test

    void contextLoads() {
        Member member = memberWriter.create(Fixture.generateMember());
        IssuePeriod issuePeriod = new IssuePeriod(LocalDateTime.now(), LocalDateTime.now().plusDays(1L));
        Coupon coupon = couponWriter.create(Fixture.generateBigSaleFashionCoupon(issuePeriod));

        MemberCoupon memberCoupon = memberCouponService.issueCoupon(member.getId(), coupon.getId());
        System.out.println(memberCoupon);

        List<MemberCouponResponse> allByMemberId = memberCouponService.findAllByMemberId(member.getId());

        System.out.println(allByMemberId);
    }
}
