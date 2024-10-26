package coupon.service;

import coupon.domain.coupon.CouponRepository;
import coupon.domain.member.MemberRepository;
import coupon.domain.membercoupon.MemberCouponRepository;
import coupon.support.extension.DatabaseCleanerExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@ExtendWith(DatabaseCleanerExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public abstract class BaseServiceTest {

    @Autowired
    protected CouponRepository couponRepository;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected MemberCouponRepository memberCouponRepository;
}
