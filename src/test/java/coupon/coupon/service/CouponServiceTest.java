package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.coupon.CouponFixture;
import coupon.member.domain.Member;
import coupon.member.domain.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@DisplayName("쿠폰 서비스 테스트")
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class CouponServiceTest {

    @Autowired
    private CouponService couponService;
    @Autowired
    private MemberRepository memberRepository;

    private Member issuer;

    @BeforeEach
    void setUp() {
        issuer = memberRepository.save(new Member("리비"));
    }

    @AfterEach
    void tearDown() {
        couponService.deleteAll();
        memberRepository.delete(issuer);
    }

    @DisplayName("쿠폰을 생성하고 저장할 수 있다")
    @Test
    void createCoupon() {
        final var couponCreateRequest = CouponFixture.TOUROOT_COUPON.getCouponCreateRequest(issuer);
        final var savedCoupon = couponService.createCoupon(couponCreateRequest);

        assertThat(savedCoupon.getId()).isNotNull();
    }

    @DisplayName("쿠폰을 조회할 수 있다")
    @Test
    void findCoupon() {
        final var couponCreateRequest = CouponFixture.TOUROOT_COUPON.getCouponCreateRequest(issuer);
        final var savedCoupon = couponService.createCoupon(couponCreateRequest);

        final var found = couponService.getCoupon(savedCoupon.getId());

        assertThat(found.getName()).isEqualTo("투룻 쿠폰");
    }

    @DisplayName("복제 지연 시간 동안 작성자가 작성한 내용을 조회할 수 있다")
    @Test
    void replicationLag() throws InterruptedException {
        final var couponCreateRequest = CouponFixture.TOUROOT_COUPON.getCouponCreateRequest(issuer);
        final var saved = couponService.createCoupon(couponCreateRequest);

        final var found = couponService.getCoupon(saved.getId());
        assertThat(found).isNotNull();
    }
}
