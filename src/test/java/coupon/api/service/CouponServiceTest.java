package coupon.api.service;

import coupon.api.repository.CouponRepository;
import coupon.api.repository.MemberRepository;
import coupon.common.ErrorConstant;
import coupon.common.exception.CouponException;
import coupon.common.exception.CouponNotFoundException;
import coupon.common.response.StorageCouponResponse;
import coupon.domain.coupon.Category;
import coupon.domain.coupon.CouponDomain;
import coupon.domain.member.MemberDomain;
import coupon.entity.Coupon;
import coupon.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CouponServiceTest {

    @Autowired
    private CouponService couponService;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("Replication이 딜레이 되더라도 쿠폰 조회에 영향이 가지 않는다.")
    void replicationDelay() {
        Coupon insertedCoupon = createCoupon();

        Coupon savedCoupon = couponService.searchCoupon(insertedCoupon.getId());
        assertThat(savedCoupon).isNotNull();
    }

    @Test
    @DisplayName("존재하지 않는 쿠폰인 경우 에러를 발생한다.")
    void searchCoupon_whenNotExist() {
        assertThatThrownBy(() -> couponService.searchCoupon(Long.MAX_VALUE))
                .isInstanceOf(CouponNotFoundException.class);
    }

    @Test
    @DisplayName("이미 같은 쿠폰을 최대로 발행한 경우 에러를 발생한다.")
    void issueCoupon() {
        Member member = createMember();
        Coupon createdCoupon = createCoupon();

        for (int i = 0; i <= Member.MAX_ISSUE_COUPON; i++) {
            couponService.issueCoupon(member.getId(), createdCoupon.getId());
        }

        assertThatThrownBy(() -> couponService.issueCoupon(member.getId(), createdCoupon.getId()))
                .isInstanceOf(CouponException.class)
                .hasMessage(ErrorConstant.ISSUED_COUPON_MAX.getMessage());
    }

    @Test
    @DisplayName("필요한 쿠폰 정보를 조회할 수 있다.")
    void searchAllCoupon() {
        Member member = createMember();
        Coupon createdCoupon = createCoupon();

        couponService.issueCoupon(member.getId(), createdCoupon.getId());

        List<StorageCouponResponse> storageCouponResponses = couponService.searchAllCoupons(member.getId());
        assertAll(
                () -> assertThat(storageCouponResponses.size()).isEqualTo(1),
                () -> assertThat(storageCouponResponses.get(0).coupon()).isEqualTo(createdCoupon.toUserStorageCoupon())
        );
    }

    private Coupon createCoupon() {
        CouponDomain coupon = new CouponDomain("coupon",
                1000,
                10000,
                Category.FASHION,
                LocalDate.now(),
                LocalDate.now().plusDays(3)
        );

        return couponService.create(coupon);
    }

    private Member createMember() {
        Member member = new Member(new MemberDomain("polla"));
        memberRepository.save(member);

        return member;
    }
}
