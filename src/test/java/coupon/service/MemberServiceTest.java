package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.dto.MemberCouponResponse;
import coupon.util.CouponFixture;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
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
    Member member;
    @Autowired
    private MemberService memberService;
    @Autowired
    private CouponService couponService;

    @BeforeEach
    void setUp() {
        member = memberService.create("Jake");
    }

    @Test
    @DisplayName("멤버를 생성하여 아이디를 부여한다.")
    void create() {
        assertThat(member.getId()).isNotNull();
        assertThat(member.getName()).isNotEmpty();
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
        Long memberId = member.getId();
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

    @Test
    @DisplayName("자신이 발급받은 모든 쿠폰을 조회한다.")
    void getIssuedCoupons() {
        // given
        List<Coupon> coupons = CouponFixture.createCoupons().stream()
                .map(couponService::create)
                .toList();
        List<MemberCoupon> memberCoupons = new ArrayList<>();
        Random random = new Random();
        Long memberId = member.getId();

        // when
        for (Coupon coupon : coupons) {
            for (int i = random.nextInt(1, 5); i > 0; i--) {
                MemberCoupon memberCoupon = memberService.issueCoupon(memberId, coupon.getId());
                memberCoupons.add(memberCoupon);
            }
        }
        List<MemberCouponResponse> issuedCoupons = memberService.getIssuedCoupons(memberId);

        List<Long> actualIds = issuedCoupons.stream().map(MemberCouponResponse::id).toList();
        List<Long> expectedIds = memberCoupons.stream().map(MemberCoupon::getId).toList();

        // then
        System.out.println("issuedCoupons = " + issuedCoupons);
        assertThat(actualIds).isEqualTo(expectedIds);
    }
}
