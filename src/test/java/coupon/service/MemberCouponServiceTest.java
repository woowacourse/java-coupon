package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.domain.vo.DiscountAmount;
import coupon.domain.vo.IssuePeriod;
import coupon.domain.vo.MinimumOrderPrice;
import coupon.domain.vo.Name;
import coupon.exception.ErrorMessage;
import coupon.exception.GlobalCustomException;
import coupon.repository.CouponRepository;
import coupon.repository.MemberRepository;
import coupon.util.DatabaseCleaner;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;

    @Autowired
    private MemberRepository memberRepository;

    @SpyBean
    private CouponRepository couponRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    private Member member;
    private Coupon coupon;

    @BeforeEach
    void setUp() {
        databaseCleaner.clear();

        Name name = new Name("쿠폰이름");
        DiscountAmount discountAmount = new DiscountAmount(1_000);
        MinimumOrderPrice minimumOrderPrice = new MinimumOrderPrice(30_000);
        IssuePeriod issuePeriod = new IssuePeriod(LocalDateTime.now(), LocalDateTime.now());
        Coupon coupon = new Coupon(name, discountAmount, minimumOrderPrice, Category.FASHION, issuePeriod);

        this.coupon = couponRepository.save(coupon);
        this.member = memberRepository.save(new Member("미아"));
    }

    @Test
    @DisplayName("사용자에게 쿠폰을 발급한다.")
    void issue() {
        // when
        MemberCoupon memberCoupon = memberCouponService.issue(member, coupon);

        // then
        assertThat(memberCoupon).isNotNull();
    }

    @Test
    @DisplayName("쿠폰 발급 기간이 아닐 경우 쿠폰 발급을 시도하면 예외가 발생한다.")
    void issueInInvalidPeriod() {
        // given
        Coupon expiredCoupon = makeExpiredCoupon();

        // when & then
        assertThatThrownBy(() -> memberCouponService.issue(member, expiredCoupon))
                .isInstanceOf(GlobalCustomException.class)
                .hasMessage(ErrorMessage.NOT_IN_COUPON_ISSUE_PERIOD.getMessage());
    }

    private Coupon makeExpiredCoupon() {
        Name name = new Name("쿠폰이름");
        DiscountAmount discountAmount = new DiscountAmount(1_000);
        MinimumOrderPrice minimumOrderPrice = new MinimumOrderPrice(30_000);
        IssuePeriod issuePeriod = new IssuePeriod(LocalDateTime.now().minusDays(3), LocalDateTime.now().minusDays(2));
        return new Coupon(name, discountAmount, minimumOrderPrice, Category.FASHION, issuePeriod);
    }

    @Test
    @DisplayName("한 명의 회원은 동일한 쿠폰을 사용한 쿠폰을 포함하여 최대 5장까지 발급할 수 있다.")
    void issueUpTo5Coupons() {
        // given
        memberCouponService.issue(member, coupon);
        memberCouponService.issue(member, coupon);
        memberCouponService.issue(member, coupon);
        memberCouponService.issue(member, coupon);
        memberCouponService.issue(member, coupon);

        // when & then
        assertThatThrownBy(() -> memberCouponService.issue(member, coupon))
                .isInstanceOf(GlobalCustomException.class)
                .hasMessage(ErrorMessage.EXCEED_ISSUE_MEMBER_COUPON.getMessage());
    }

    @Test
    @DisplayName("회원의 쿠폰 목록을 조회할 때 회원에게 발급된 쿠폰 정보와 쿠폰 정보를 함께 조회한다.")
    void findAllIssuedCoupons() {
        // given
        memberCouponService.issue(member, coupon);

        // when
        List<MemberCoupon> memberCoupons = memberCouponService.findAllIssuedCoupons(member);

        // then
        assertAll(() -> {
            assertThat(memberCoupons).extracting(MemberCoupon::getIssuedAt)
                    .doesNotContainNull();
            assertThat(memberCoupons).extracting(MemberCoupon::getCoupon)
                    .flatExtracting(Coupon::getCategory)
                    .doesNotContainNull();
        });
    }

    @Test
    @DisplayName("회원의 쿠폰 목록을 조회할 때 쿠폰 정보는 Look aside 캐시를 사용한다.")
    void findCouponInCache() {
        // given
        memberCouponService.issue(member, coupon);
        memberCouponService.findAllIssuedCoupons(member); // 이미 한 번 조회됨

        // when
        memberCouponService.findAllIssuedCoupons(member);

        // then
        verify(couponRepository, times(1)).findAllByIdIn(anySet());
    }
}
