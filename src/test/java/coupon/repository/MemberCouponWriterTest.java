package coupon.repository;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MemberCouponWriterTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberCouponWriter memberCouponWriter;

    @Test
    @DisplayName("회원에게 발급된 쿠폰이 5개 이상이면 예외를 발생한다.")
    void throw_exception_with_issued_count_over_five() {
        final Member member = memberRepository.save(new Member(1L));
        final Coupon coupon = couponRepository.save(new Coupon(
                "추석 할인 쿠폰",
                new Money(new BigDecimal(1000)),
                new Money(new BigDecimal(30000)),
                Category.FOOD,
                LocalDateTime.of(2024, 9, 1, 0, 0),
                LocalDateTime.of(2024, 9, 30, 23, 59)
        ));
        IntStream.rangeClosed(0, 5).forEach(nu -> memberCouponWriter.create(member, coupon));
        assertThatThrownBy(() -> memberCouponWriter.create(member, coupon))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
