package coupon.membercoupon.service;

import coupon.coupon.dto.CouponResponse;
import coupon.coupon.service.CouponService;
import coupon.fixture.CouponFixture;
import coupon.fixture.MemberCouponFixture;
import coupon.member.dto.MemberRequest;
import coupon.member.dto.MemberResponse;
import coupon.member.service.MemberService;
import coupon.membercoupon.dto.MemberCouponRequest;
import coupon.membercoupon.dto.MemberCouponResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CouponService couponService;

    private CouponResponse coupon;
    private MemberResponse member;

    @BeforeEach
    void setUp() {
        coupon = couponService.create(CouponFixture.COUPON_CREATE_REQUEST());
        member = memberService.create(new MemberRequest("name"));
    }

    @Test
    @DisplayName("회원에게 쿠폰을 발급할 수 있다.")
    void issueCoupon() {
        MemberCouponRequest request = MemberCouponFixture.MEMBER_COUPON_REQUEST(coupon.id(), member.id());

        MemberCouponResponse response = memberCouponService.issueCoupon(request);

        long memberId = response.memberResponse().id();
        assertThat(memberId).isEqualTo(member.id());
    }

    @Test
    @DisplayName("일치하는 쿠폰 아이디가 없다면 예외가 발생한다.")
    void invalidCouponId() {
        MemberCouponRequest request = MemberCouponFixture.MEMBER_COUPON_REQUEST(0, member.id());

        assertThatThrownBy(() -> memberCouponService.issueCoupon(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 쿠폰 아이디와 일치하는 쿠폰을 찾을 수 없습니다. 입력된 id:0");
    }

    @Test
    @DisplayName("일치하는 멤버 아이디가 없다면 예외가 발생한다.")
    void invalidMemberId() {
        MemberCouponRequest request = MemberCouponFixture.MEMBER_COUPON_REQUEST(coupon.id(), 0);

        assertThatThrownBy(() -> memberCouponService.issueCoupon(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("일치하는 멤버를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("한 명의 회원에게 동일한 쿠폰을 5장을 초과하여 발급하려 하면 예외가 발생한다.")
    void invalidIssuedCouponSize() {
        MemberCouponRequest request = MemberCouponFixture.MEMBER_COUPON_REQUEST(coupon.id(), member.id());
        for (int i = 0; i < 5; i++) {
            memberCouponService.issueCoupon(request);
        }

        assertThatThrownBy(() -> memberCouponService.issueCoupon(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("한 명의 회원에게 동일한 쿠폰을 최대 5장까지 발급 가능합니다.");
    }

    @Test
    @DisplayName("쿠폰을 발급 가능한 날짜가 아니면 예외가 발생한다.")
    void invalidIssuancePeriod() {
        MemberCouponRequest request = MemberCouponFixture.MEMBER_COUPON_REQUEST(coupon.id(), member.id(), LocalDate.now().minusDays(1));

        assertThatThrownBy(() -> memberCouponService.issueCoupon(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("쿠폰을 발급 가능한 날짜가 아닙니다.");
    }

    @Transactional
    @Test
    @DisplayName("회원의 쿠폰 목록을 조회할 수 있다.")
    void findAllMemberCoupon() {
        MemberCouponRequest request = MemberCouponFixture.MEMBER_COUPON_REQUEST(coupon.id(), member.id());
        memberCouponService.issueCoupon(request);

        List<MemberCouponResponse> result = memberCouponService.findAllMemberCoupon(member.id());

        assertThat(result).hasSize(1);
    }
}
