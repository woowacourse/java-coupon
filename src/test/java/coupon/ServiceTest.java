package coupon;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import coupon.coupon.repository.CouponRepository;
import coupon.member.repository.MemberRepository;
import coupon.membercoupon.repository.MemberCouponRepository;
import coupon.util.DatabaseCleanerExtension;

@ExtendWith(DatabaseCleanerExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public abstract class ServiceTest {

    @Autowired
    protected MemberRepository memberRepository;
    @Autowired
    protected MemberCouponRepository memberCouponRepository;
    @Autowired
    protected CouponRepository couponRepository;
}
