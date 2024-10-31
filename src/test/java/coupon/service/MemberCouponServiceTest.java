package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;
import coupon.domain.member.Member;
import coupon.repository.CouponCache;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.repository.MemberRepository;
import coupon.service.dto.MemberCouponResponse;

@SpringBootTest
public class MemberCouponServiceTest {

    @Autowired
    MemberCouponService memberCouponService;

    @Autowired
    CouponService couponService;

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    MemberCouponRepository memberCouponRepository;

    @Autowired
    MemberRepository memberRepository;

    private Coupon coupon;
    private Member member;

    @BeforeEach
    public void setUp() {
        coupon = new Coupon("name",
                Category.FOOD,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                10000,
                1000);

        couponService.create(coupon);
        member = new Member();
        memberService.save(member);
    }

    @AfterEach
    public void tearDown() {
        memberCouponRepository.deleteAll();
        couponRepository.deleteAll();
        memberRepository.deleteAll();
        CouponCache.clear();
    }

    @Test
    void 쿠폰과_회원에게_발급된_쿠폰을_조회한다() throws InterruptedException {
        memberCouponService.create(member, coupon.getId());

        Thread.sleep(3000);
        List<MemberCouponResponse> memberCouponResponses = memberCouponService.readAll(member.getId());
        MemberCouponResponse memberCouponResponse = memberCouponResponses.get(0);

        assertAll(
                () -> assertThat(memberCouponResponse.memberCoupon().getMemberId()).isEqualTo(member.getId()),
                () -> assertThat(memberCouponResponse.coupon().getId()).isEqualTo(coupon.getId())
        );
    }


    @Test
    void 동일한_쿠폰을_5장_초과_생성하면_예외가_발생한다() {
        memberCouponService.create(member, coupon.getId());
        memberCouponService.create(member, coupon.getId());
        memberCouponService.create(member, coupon.getId());
        memberCouponService.create(member, coupon.getId());
        memberCouponService.create(member, coupon.getId());

        assertThatThrownBy(() -> memberCouponService.create(member, coupon.getId()))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("발급 가능한 수량은 최대 5 개 입니다");
    }

    @Test
    void 동일한_쿠폰은_5장_이하_생성이_가능하다() {
        memberCouponService.create(member, coupon.getId());
        memberCouponService.create(member, coupon.getId());
        memberCouponService.create(member, coupon.getId());
        memberCouponService.create(member, coupon.getId());

        assertDoesNotThrow(() -> memberCouponService.create(member, coupon.getId()));
    }
}
