package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import coupon.domain.MemberCoupon;
import coupon.domain.coupon.Coupon;
import coupon.domain.member.Member;
import coupon.dto.MemberCouponResponse;
import coupon.dto.MemberCouponResponses;
import coupon.fixture.CouponFixture;
import coupon.fixture.MemberFixture;
import coupon.repository.CouponRepository;
import coupon.repository.MemberRepository;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cache.CacheManager;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MemberCouponServiceTest {

    private static int MAX_ISSUED_COUNT = 5;
    private static String CACHE_NAME = "member_coupon";

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberCouponService memberCouponService;

    @Autowired
    private CacheManager cacheManager;

    @AfterEach
    void tearDown() {
        Objects.requireNonNull(cacheManager.getCache(CACHE_NAME))
                .clear();
    }

    @DisplayName("동일 쿠폰을 최대 발급 수량을 초과하여 발급받을 수 없다")
    @Test
    void canNotIssueCoupon_Over_MaxIssuedCount() {
        Member member = memberRepository.save(MemberFixture.MEMBER_FIXTURE);
        Coupon coupon = couponRepository.save(CouponFixture.COUPON_FIXTURE);

        for (int i = 0; i < MAX_ISSUED_COUNT; i++) {
            memberCouponService.issue(member, coupon);
        }

        assertThatThrownBy(() -> memberCouponService.issue(member, coupon))
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("멤버에게 쿠폰을 발급할 수 있다")
    @Test
    void issueCoupon() {
        Member member = memberRepository.save(MemberFixture.MEMBER_FIXTURE);
        Coupon coupon = couponRepository.save(CouponFixture.COUPON_FIXTURE);

        assertThatCode(() -> memberCouponService.issue(member, coupon))
                .doesNotThrowAnyException();
    }

    @DisplayName("멤버에게 해당하는 쿠폰내역을 조회할 수 있다")
    @Test
    void readMemberCoupon() {
        Member member = memberRepository.save(MemberFixture.MEMBER_FIXTURE);
        Coupon coupon = couponRepository.save(CouponFixture.COUPON_FIXTURE);

        MemberCoupon issuedCoupon1 = memberCouponService.issue(member, coupon);
        MemberCoupon issuedCoupon2 = memberCouponService.issue(member, coupon);

        List<Long> memberCouponResponseIds = memberCouponService.findAllMemberCoupon(member)
                .memberCoupons()
                .stream()
                .map(memberCouponResponse -> memberCouponResponse.id())
                .toList();

        assertThat(memberCouponResponseIds).containsOnly(issuedCoupon1.getId(), issuedCoupon2.getId());
    }

    @DisplayName("멤버의 쿠폰 내역은 캐시를 통해 조회가 가능하다")
    @Test
    void readMemberCoupon_Through_Cache() {
        Member member = memberRepository.save(MemberFixture.MEMBER_FIXTURE);
        Coupon coupon = couponRepository.save(CouponFixture.COUPON_FIXTURE);

        MemberCoupon issuedCoupon = memberCouponService.issue(member, coupon);
        memberCouponService.findAllMemberCoupon(member);

        List<MemberCouponResponse> actual = Objects.requireNonNull(cacheManager.getCache(CACHE_NAME))
                .get(member.getId(), MemberCouponResponses.class)
                .memberCoupons();

        assertAll(
                () -> assertThat(actual).hasSize(1),
                () -> assertThat(actual.get(0).id()).isEqualTo(issuedCoupon.getId())
        );
    }
}
