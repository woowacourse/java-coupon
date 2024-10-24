package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.entity.CouponEntity;
import coupon.entity.MemberEntity;
import coupon.repository.CouponRepository;
import coupon.repository.MemberRepository;
import coupon.support.CacheCleanerExtension;
import coupon.support.DatabaseCleanerExtension;
import coupon.support.Fixture;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@ExtendWith(value = {DatabaseCleanerExtension.class, CacheCleanerExtension.class})
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Coupon coupon;
    private List<Coupon> coupons;
    private Member member;

    @BeforeEach
    void setUp() {
        coupon = couponRepository.save(new CouponEntity(Fixture.COUPON)).toDomain();
        coupons = IntStream.range(0, 3)
                .mapToObj(i -> couponRepository.save(new CouponEntity(Fixture.COUPON)).toDomain())
                .toList();
        member = memberRepository.save(new MemberEntity(Fixture.MEMBER)).toDomain();
    }

    @DisplayName("쿠폰이 5장 발급됐다면, 추가 발급이 불가하다.")
    @Test
    void memberCouponIssueTest() throws Exception {
        // given
        IntStream.range(0, 5).forEach(i -> memberCouponService.create(member, coupon));
        waitForSeconds(3);

        // when, then
        assertThatThrownBy(() -> memberCouponService.create(member, coupon))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 5장의 쿠폰을 발급받았습니다.");
    }

    @DisplayName("쿠폰이 4장 발급됐다면, 한 장 더 발급 가능하다.")
    @Test
    void memberCouponIssueTest2() throws Exception {
        // given
        IntStream.range(0, 4).forEach(i -> memberCouponService.create(member, coupon));
        waitForSeconds(3);

        // when
        assertThatCode(() -> memberCouponService.create(member, coupon))
                .doesNotThrowAnyException();
        waitForSeconds(3);

        // then
        assertThatThrownBy(() -> memberCouponService.create(member, coupon))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 5장의 쿠폰을 발급받았습니다.");
    }

    @DisplayName("회원이 발급받은 쿠폰을 조회한다.")
    @Test
    void findAllByMemberTest() throws Exception {
        // given
        IntStream.range(0, 3).forEach(i -> memberCouponService.create(member, coupons.get(i)));
        waitForSeconds(3);

        // when
        List<MemberCoupon> memberCoupons = memberCouponService.findAllByMember(member);

        // then
        assertAll(
                () -> assertThat(memberCoupons).hasSize(3),
                () -> assertThat(memberCoupons)
                        .extracting(MemberCoupon::getCoupon)
                        .containsExactlyElementsOf(coupons)
        );
    }

    void waitForSeconds(int seconds) throws Exception {
        Thread.sleep(1000L * seconds);
    }
}
