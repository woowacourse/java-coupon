package coupon.domain.membercoupon.repository;

import static org.assertj.core.api.Assertions.assertThat;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponCategory;
import coupon.domain.coupon.CouponPeriod;
import coupon.domain.coupon.Name;
import coupon.domain.coupon.TestDiscountPolicy;
import coupon.domain.coupon.repository.CouponRepository;
import coupon.domain.member.Member;
import coupon.domain.member.repository.MemberRepository;
import coupon.domain.membercoupon.MemberCoupon;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MemberCouponRepositoryAdaptorTest {

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 아이디와 쿠폰 아이디로 회원에게 발급된 쿠폰을 조회한다.")
    @Transactional
    void findAllByMemberIdAndCouponId() {
        Member member = saveMember();
        Coupon coupon = saveCoupon();
        MemberCoupon memberCoupon = saveMemberCoupon(member, coupon);

        Member otherMember = saveMember();
        MemberCoupon otherMemberCoupon = saveMemberCoupon(otherMember, coupon);

        Coupon otherCoupon = saveCoupon();
        MemberCoupon memberOtherCoupon = saveMemberCoupon(member, otherCoupon);

        otherMemberCoupon = memberCouponRepository.save(otherMemberCoupon);

        List<MemberCoupon> result = memberCouponRepository.findAllByMemberIdAndCouponId(
                member.getId(),
                coupon.getId()
        );

        assertThat(result)
                .contains(memberCoupon)
                .doesNotContain(otherMemberCoupon, memberOtherCoupon);
    }

    private MemberCoupon saveMemberCoupon(Member member, Coupon coupon) {
        LocalDate startDate = LocalDate.of(2024, 11, 1);
        LocalDate endDate = LocalDate.of(2024, 11, 3);
        MemberCoupon memberCoupon = new MemberCoupon(coupon, member, false, startDate, endDate);
        return memberCouponRepository.save(memberCoupon);
    }

    @Test
    @DisplayName("회원 아이디로 회원에게 발급된 쿠폰을 조회한다.")
    @Transactional
    void findAllByMemberId() {
        Member member = saveMember();
        Coupon coupon = saveCoupon();
        MemberCoupon memberCoupon = saveMemberCoupon(member, coupon);

        Member otherMember = saveMember();
        MemberCoupon otherMemberCoupon = saveMemberCoupon(otherMember, coupon);

        Coupon otherCoupon = saveCoupon();
        MemberCoupon memberOtherCoupon = saveMemberCoupon(member, otherCoupon);

        otherMemberCoupon = memberCouponRepository.save(otherMemberCoupon);

        List<MemberCoupon> result = memberCouponRepository.findAllByMemberId(member.getId());

        assertThat(result)
                .contains(memberCoupon, memberOtherCoupon)
                .doesNotContain(otherMemberCoupon);
    }

    @Test
    @DisplayName("회원에게 쿠폰을 발급한다.")
    void save() {
        Coupon coupon = saveCoupon();
        Member member = saveMember();
        LocalDate startDate = LocalDate.of(2024, 11, 1);
        LocalDate endDate = LocalDate.of(2024, 11, 3);
        MemberCoupon memberCoupon = new MemberCoupon(coupon, member, false, startDate, endDate);
        MemberCoupon save = memberCouponRepository.save(memberCoupon);

        assertThat(save.getId())
                .isNotNull();
    }

    private Coupon saveCoupon() {
        Name name = new Name("할인 쿠폰");
        TestDiscountPolicy discountPolicy = new TestDiscountPolicy(1000, 30000);
        LocalDate startDate = LocalDate.of(2024, 11, 1);
        LocalDate endDate = LocalDate.of(2024, 11, 3);
        CouponPeriod couponPeriod = new CouponPeriod(startDate, endDate);
        Coupon coupon = new Coupon(name, discountPolicy, CouponCategory.FASHION, couponPeriod);
        return couponRepository.save(coupon);
    }

    private Member saveMember() {
        Member member = new Member(
                "email@email.com", "password"
        );
        return memberRepository.save(member);
    }
}
