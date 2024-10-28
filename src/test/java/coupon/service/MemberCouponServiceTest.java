package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.domain.Coupon;
import coupon.domain.CouponCategory;
import coupon.domain.CouponRepository;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.domain.MemberCouponRepository;
import coupon.domain.MemberRepository;
import coupon.service.dto.MemberCouponResponses;
import java.time.LocalDateTime;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member member;
    private Coupon coupon;

    @BeforeEach
    void setUp() {
        memberCouponRepository.deleteAllInBatch();
        couponRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();

        member = new Member("jazz");
        coupon = new Coupon(
                "coupon",
                1000, 5000,
                CouponCategory.FASHION,
                LocalDateTime.now(), LocalDateTime.now().plusDays(3),
                100, 0
        );
    }

    @DisplayName("멤버 쿠폰 발급 시 존재하지 않는 멤버일 경우 예외를 발생시킨다.")
    @Test
    void throwsWhenCreateCouponNotExistsMember() {
        assertThatThrownBy(() -> memberCouponService.create(1L, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @DisplayName("멤버 쿠폰 발급 시 존재하지 않는 쿠폰일 경우 예외를 발생시킨다.")
    @Test
    void throwsWhenCreateCouponNotExistsCoupon() {
        Member savedMember = memberRepository.save(member);

        assertThatThrownBy(() -> memberCouponService.create(savedMember.getId(), 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("쿠폰이 유효하지 않습니다.");
    }

    @DisplayName("멤버 쿠폰 발급 시 최대 발급 가능한 수량을 초과할 경우 예외를 발생시킨다.")
    @Test
    void throwsWhenCreateCouponExceedsMaxIssuableCoupons() {
        Member savedMember = memberRepository.save(member);
        Coupon savedCoupon = couponRepository.save(coupon);

        IntStream.range(0, 5).forEach(i -> memberCouponService.create(savedMember.getId(), savedCoupon.getId()));

        long memberId = savedMember.getId();
        long couponId = savedCoupon.getId();

        assertThatThrownBy(() -> memberCouponService.create(memberId, couponId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("발급 가능한 쿠폰 최대 개수는 5개입니다.");
    }

    @DisplayName("멤버 쿠폰이 정상적으로 발급된다.")
    @Test
    void createMemberCouponSuccessfully() {
        Member savedMember = memberRepository.save(member);
        Coupon savedCoupon = couponRepository.save(coupon);

        assertThatNoException()
                .isThrownBy(() -> memberCouponService.create(savedMember.getId(), savedCoupon.getId()));
    }

    @DisplayName("멤버 쿠폰 조회 시 존재하지 않는 멤버일 경우 예외를 발생시킨다.")
    @Test
    void throwsWhenGetMemberCouponsNotExistsMember() {
        assertThatThrownBy(() -> memberCouponService.getMemberCoupons(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }

    @DisplayName("멤버 쿠폰을 정상적으로 조회한다.")
    @Test
    void getMemberCouponsSuccessfully() {
        Member savedMember = memberRepository.save(member);
        Coupon savedCoupon1 = couponRepository.save(coupon);
        Coupon savedCoupon2 = couponRepository.save(coupon);

        memberCouponRepository.save(new MemberCoupon(savedMember, savedCoupon1));
        memberCouponRepository.save(new MemberCoupon(savedMember, savedCoupon2));

        MemberCouponResponses memberCoupons = memberCouponService.getMemberCoupons(1L);

        assertThat(memberCoupons.memberCouponResponses.size()).isEqualTo(2);
    }
}
