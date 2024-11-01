package coupon.utils;

import coupon.coupon.repository.CouponRepository;
import coupon.member.repository.MemberRepository;
import coupon.memberCoupon.repository.MemberCouponRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class IsolatedTest {

    @Autowired
    protected CouponRepository couponRepository;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected MemberCouponRepository memberCouponRepository;

    @AfterEach
    void cleanUp() {
        memberCouponRepository.deleteAll();
        couponRepository.deleteAll();
        memberRepository.deleteAll();
    }
}
