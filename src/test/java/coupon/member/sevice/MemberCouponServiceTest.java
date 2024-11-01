package coupon.member.sevice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.coupon.domain.CouponCategory;
import coupon.coupon.domain.CouponEntity;
import coupon.coupon.domain.MemberCoupon;
import coupon.coupon.dto.MemberCouponRequest;
import coupon.coupon.dto.MemberCouponResponse;
import coupon.coupon.repository.CouponRepository;
import coupon.coupon.repository.MemberCouponRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class MemberCouponServiceTest {

    @Autowired
    MemberCouponService memberCouponService;

    @Autowired
    MemberCouponRepository memberCouponRepository;

    @Autowired
    CouponRepository couponRepository;

    @Test
    void 회원_쿠폰을_생성한다() throws InterruptedException {
        // given
        int couponId = 1;
        MemberCouponRequest request = new MemberCouponRequest(couponId, 1);

        // when
        long memberCouponId = memberCouponService.issueCoupon(request);
        Thread.sleep(2000);

        // then
        assertThat(memberCouponRepository.findById(memberCouponId).get().getCouponId()).isEqualTo(couponId);
    }

    @Test
    void 중복된_쿠폰을_회원당_기준_수량_이상_발행하면_예외를_발생한다() throws InterruptedException {
        // given
        LocalDateTime couponStartAt = LocalDateTime.of(1, 1, 1, 1, 1);
        int couponId = 1;
        int memberId = 1;
        memberCouponRepository.saveAll(
                List.of(
                        new MemberCoupon(couponId, memberId, false, couponStartAt, couponStartAt.plusDays(1)),
                        new MemberCoupon(couponId, memberId, false, couponStartAt, couponStartAt.plusDays(2)),
                        new MemberCoupon(couponId, memberId, false, couponStartAt, couponStartAt.plusDays(3)),
                        new MemberCoupon(couponId, memberId, false, couponStartAt, couponStartAt.plusDays(4)),
                        new MemberCoupon(couponId, memberId, false, couponStartAt, couponStartAt.plusDays(5))
                )
        );
        MemberCouponRequest request = new MemberCouponRequest(couponId, memberId);
        Thread.sleep(2000);

        // when &&  then
        assertThatThrownBy(() -> memberCouponService.issueCoupon(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("동일한 쿠폰은 5장까지 발급할 수 있어요.");
    }

    @Test
    void 회원에_해당하는_쿠폰을_조회한다() throws InterruptedException {
        // given
        LocalDateTime couponStartAt = LocalDateTime.of(1, 1, 1, 1, 1);
        CouponEntity coupon = couponRepository.save(
                new CouponEntity("couponName", 5000, 50000, CouponCategory.FOOD,
                        couponStartAt, couponStartAt.plusDays(1)));
        int memberId = 1;
        memberCouponRepository.save(
                new MemberCoupon(coupon.getId(), memberId, false, couponStartAt, couponStartAt.plusDays(1))
        );
        Thread.sleep(2000);

        // when
        List<MemberCouponResponse> couponByMember = memberCouponService.getCouponByMember(memberId);

        // then
        assertThat(couponByMember).hasSize(1);
    }
}
