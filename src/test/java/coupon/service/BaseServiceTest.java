package coupon.service;

import coupon.domain.repository.CouponRepository;
import coupon.domain.repository.MemberCouponRepository;
import coupon.domain.repository.MemberRepository;
import coupon.support.DatabaseClearExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@ExtendWith(DatabaseClearExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class BaseServiceTest {

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberCouponRepository memberCouponRepository;
}
