package coupon.membercoupon.repository;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import coupon.coupons.domain.Category;
import coupon.coupons.domain.Coupon;
import coupon.coupons.repository.CouponRepository;
import coupon.member.domain.Member;
import coupon.member.repository.MemberRepository;
import coupon.membercoupon.domain.MemberCoupon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Sql(scripts = "/schema.sql")
class MemberCouponRepositoryTest {

    @Autowired
    private MemberCouponRepository memberCouponRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CouponRepository couponRepository;

    @DisplayName("회원 Id로 해당하는 모든 MemberCoupon을 조회할 수 있다.")
    @Test
    void findAllByMemberId() {
        Member member = memberRepository.save(new Member("회원"));
        Coupon coupon1 = couponRepository.save(new Coupon("유효한 쿠폰1", 1000, 5000, Category.FASHION.name(), LocalDateTime.now(), LocalDateTime.now()));
        Coupon coupon2 = couponRepository.save(new Coupon("유효한 쿠폰2", 1000, 5000, Category.FASHION.name(), LocalDateTime.now(), LocalDateTime.now()));
        MemberCoupon memberCoupon1 = memberCouponRepository.save(new MemberCoupon(coupon1, member, LocalDateTime.now()));
        MemberCoupon memberCoupon2 = memberCouponRepository.save(new MemberCoupon(coupon2, member, LocalDateTime.now()));

        assertAll(
                () -> assertThat(memberCouponRepository.findAllByMemberId(member.getId())
                        .get(0).getId()).isEqualTo(memberCoupon1.getId()),
                () -> assertThat(memberCouponRepository.findAllByMemberId(member.getId())
                        .get(1).getId()).isEqualTo(memberCoupon2.getId())
        );
    }
}
