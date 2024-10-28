package coupon.membercoupon.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.stream.IntStream;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;

import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.CouponRepository;
import coupon.member.domain.Member;
import coupon.member.domain.MemberRepository;
import coupon.membercoupon.domain.MemberCoupon;
import coupon.membercoupon.dto.FindAllMemberCouponResponse;

@SpringBootTest
@Transactional
class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CouponRepository couponRepository;

    private Member member;
    private Coupon coupon;
    private LocalDate issueStartDate;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(new Member("hi"));
        issueStartDate = LocalDate.parse("2024-10-11");
        coupon = couponRepository.save(new Coupon("행복한 50% 쿠폰", 1_000L, 10_000L, issueStartDate, issueStartDate.plusDays(7)));
    }

    @Test
    @DisplayName("멤버에게 쿠폰 발급 성공")
    void createMemberCoupon() {
        memberCouponService.createMemberCoupon(new MemberCoupon(member.getId(), coupon.getId(), false, issueStartDate.plusDays(3)));

        assertThat(memberCouponService.getMemberCouponsByMemberId(member.getId())).isNotNull();
    }

    @Test
    @DisplayName("멤버에게 쿠폰 발급 실패: 최대 발급 가능 수 초과")
    void createMemberCouponWhenCouponOverIssued() {
        IntStream.range(0, 5)
                .forEach(ignore -> memberCouponService.createMemberCoupon(new MemberCoupon(member.getId(), coupon.getId(), false, issueStartDate.plusDays(3))));

        assertThatThrownBy(() ->  memberCouponService.createMemberCoupon(new MemberCoupon(member.getId(), coupon.getId(), false, issueStartDate.plusDays(3))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("발급 가능한 최대 쿠폰 개수를 초과하였습니다. memberId: " + member.getId());
    }

    @Test
    @DisplayName("멤버에게 쿠폰 발급된 쿠폰 목록 조회")
    void getMemberCouponsByMemberId() {
        IntStream.range(0, 5)
                .forEach(ignore -> memberCouponService.createMemberCoupon(new MemberCoupon(member.getId(), coupon.getId(), false, issueStartDate.plusDays(3))));

        FindAllMemberCouponResponse memberCouponsResponses = memberCouponService.getMemberCouponsByMemberId(member.getId());
        assertThat(memberCouponsResponses.memberCoupons()).hasSize(5);
        assertThat(memberCouponsResponses.memberCoupons().get(0).coupon().getName())
                .isEqualTo(coupon.getName());
    }
}
