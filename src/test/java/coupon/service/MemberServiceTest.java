package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.util.CouponFixture;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MemberServiceTest {

    private static final Logger log = LoggerFactory.getLogger(MemberServiceTest.class);
    @Autowired
    private MemberService memberService;
    @Autowired
    private CouponService couponService;

    @Test
    @DisplayName("멤버를 생성하여 아이디를 부여한다.")
    void create() {
        // given
        String name = "Jake";

        // when
        Member member = memberService.create(name);

        // then
        assertThat(member.getId()).isNotNull();
        assertThat(member.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("한 사용자가 쿠폰 발급을 9번 동시에 할 경우 5번만 발급된다.")
    void issueMaxMemberCouponsConcurrent() throws InterruptedException {
        // given
        int nThreads = 9;
        int expectedSuccessCount = 5;
        ExecutorService pool = Executors.newFixedThreadPool(nThreads);
        CountDownLatch latch = new CountDownLatch(nThreads);
        AtomicInteger successCount = new AtomicInteger(0);
        List<MemberCoupon> issuedCoupons = new CopyOnWriteArrayList<>();

        // when
        Long couponId = couponService.create(CouponFixture.createCoupon()).getId();
        Long memberId = memberService.create("Jake").getId();
        for (int i = 0; i < nThreads; i++) {
            pool.submit(() -> {
                try {
                    MemberCoupon memberCoupon = memberService.issueCoupon(memberId, couponId);
                    issuedCoupons.add(memberCoupon);
                    successCount.incrementAndGet();
                } catch (Exception ex) {
                    log.error(ex.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        // then
        System.out.println("issuedCoupons = " + issuedCoupons);
        assertThat(successCount.get()).isEqualTo(expectedSuccessCount);
    }
}
