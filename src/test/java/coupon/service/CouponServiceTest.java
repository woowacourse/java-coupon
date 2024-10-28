package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponRepository;
import coupon.domain.member.Member;
import coupon.domain.member.MemberRepository;
import coupon.domain.membercounpon.MemberCoupon;
import coupon.domain.membercounpon.MemberCouponRepository;
import coupon.fixture.CouponFixture;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class CouponServiceTest {

    @Autowired
    private CouponService couponService;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CacheManager cacheManager;

    private Coupon coupon;

    private Member member;

    @BeforeEach
    void setup() {
        coupon = CouponFixture.PRAM_COUPON.coupon();
        member = new Member("daon", "1234");
        memberRepository.save(member);
        couponRepository.save(coupon);
        cacheManager.getCache(CouponService.COUPON_CACHE_NAME).clear();
    }

    @AfterEach
    void teardown() {
        memberCouponRepository.deleteAllInBatch();
        couponRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("쿠폰 생성시 회원이 존재하지 않으면 예외가 발생한다.")
    @Test
    void insertWithInvalidMemberId() {
        long id = -1;

        assertThatThrownBy(() -> couponService.create(id, coupon))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("회원이 존재하지 않습니다.");
    }

    @DisplayName("회원이 소유한 쿠폰이 5장보다 많으면 예외가 발생한다.")
    @Test
    void insertWithMoreThanCouponLimitSize() {
        Coupon coupon1 = couponRepository.save(CouponFixture.DAON_COUPON.coupon());
        Coupon coupon2 = couponRepository.save(CouponFixture.BLACKJACK_COUPON.coupon());
        Coupon coupon3 = couponRepository.save(CouponFixture.CHESS_COUPON.coupon());
        Coupon coupon4 = couponRepository.save(CouponFixture.BASEBALL_COUPON.coupon());
        Coupon coupon5 = couponRepository.save(CouponFixture.LOTTO_COUPON.coupon());
        List<MemberCoupon> memberCoupons = List.of(
                new MemberCoupon(coupon1.getId(), member.getId()),
                new MemberCoupon(coupon2.getId(), member.getId()),
                new MemberCoupon(coupon3.getId(), member.getId()),
                new MemberCoupon(coupon4.getId(), member.getId()),
                new MemberCoupon(coupon5.getId(), member.getId())
        );
        memberCouponRepository.saveAll(memberCoupons);

        assertThatThrownBy(() -> couponService.create(member.getId(), coupon))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("한 회원당 %d장만 쿠폰 발급이 가능합니다.".formatted(5));
    }

    @DisplayName("쿠폰을 생성하면 캐시가 무효화된다.")
    @Test
    void createCouponsWithCacheEviction() {
        Coupon newCoupon = CouponFixture.LOTTO_COUPON.coupon();

        couponService.findAllCoupons(member.getId());
        couponService.create(member.getId(), newCoupon);

        assertThat(cacheManager.getCache(CouponService.COUPON_CACHE_NAME).get(member.getId())).isNull();
    }

    @DisplayName("회원이 소유한 쿠폰을 모두 조회한다.")
    @Test
    void findAllCoupons() {
        Coupon coupon1 = couponRepository.save(CouponFixture.DAON_COUPON.coupon());
        Coupon coupon2 = couponRepository.save(CouponFixture.BLACKJACK_COUPON.coupon());
        Coupon coupon3 = couponRepository.save(CouponFixture.CHESS_COUPON.coupon());
        Coupon coupon4 = couponRepository.save(CouponFixture.BASEBALL_COUPON.coupon());
        Coupon coupon5 = couponRepository.save(CouponFixture.LOTTO_COUPON.coupon());
        List<MemberCoupon> memberCoupons = List.of(
                new MemberCoupon(coupon1.getId(), member.getId()),
                new MemberCoupon(coupon2.getId(), member.getId()),
                new MemberCoupon(coupon3.getId(), member.getId()),
                new MemberCoupon(coupon4.getId(), member.getId()),
                new MemberCoupon(coupon5.getId(), member.getId())
        );
        memberCouponRepository.saveAll(memberCoupons);

        List<Coupon> results = couponService.findAllCoupons(member.getId());

        assertThat(results).hasSize(5);
    }

    @DisplayName("회원이 소유한 쿠폰 조획 결과가 쿠키에 존재하면 데이터베이스에 요청을 보내지 않는다.")
    @Test
    void findAllCouponsWithCacheHit() {
        Coupon coupon1 = couponRepository.save(CouponFixture.DAON_COUPON.coupon());
        Coupon coupon2 = couponRepository.save(CouponFixture.BLACKJACK_COUPON.coupon());
        Coupon coupon3 = couponRepository.save(CouponFixture.CHESS_COUPON.coupon());
        Coupon coupon4 = couponRepository.save(CouponFixture.BASEBALL_COUPON.coupon());
        List<MemberCoupon> memberCoupons = List.of(
                new MemberCoupon(coupon1.getId(), member.getId()),
                new MemberCoupon(coupon2.getId(), member.getId()),
                new MemberCoupon(coupon3.getId(), member.getId()),
                new MemberCoupon(coupon4.getId(), member.getId())
        );
        memberCouponRepository.saveAll(memberCoupons);
        long memberId = member.getId();

        List<Coupon> coupons = couponService.findAllCoupons(memberId);
        assertThat(cacheManager.getCache(CouponService.COUPON_CACHE_NAME).get(memberId).get()).isSameAs(coupons);
        List<Coupon> couponsAgain = couponService.findAllCoupons(memberId);
        assertThat(couponsAgain).isSameAs(coupons);
        assertThat(cacheManager.getCache(CouponService.COUPON_CACHE_NAME).get(memberId).get()).isSameAs(coupons);
    }

    @DisplayName("id로 조회한다.")
    @Test
    void findById() {
        Coupon foundCoupon = couponService.getCoupon(coupon.getId());
        assertThat(foundCoupon).isNotNull();
    }

    @DisplayName("복제 지연 테스트")
    @Test
    void replicaDelayTest() {
        Coupon coupon = CouponFixture.PRAM_COUPON.coupon();
        Coupon savedCoupon = couponService.create(member.getId(), coupon);
        Coupon result = couponService.getCoupon(savedCoupon.getId());
        assertThat(result).isNotNull();
    }
}
