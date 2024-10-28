package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.entity.Coupon;
import coupon.entity.CouponCategory;
import coupon.entity.Member;
import coupon.entity.MemberCoupon;
import coupon.exception.CouponException;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.repository.MemberRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.cache.RedisCacheManager;

@SpringBootTest
class MemberCouponServiceTest {

    @Autowired
    private RedisCacheManager redisCacheManager;

    @Autowired
    private MemberCouponService memberCouponService;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member member;
    @Autowired
    private CouponRepository couponRepository;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(Member.builder()
                .name("member")
                .email("email@email.com")
                .password("password")
                .build());
    }

    @AfterEach
    void tearDown() {
    	Objects.requireNonNull(redisCacheManager.getCache("coupon")).clear();
        couponRepository.deleteAll();
        memberCouponRepository.deleteAll();
    }

    @DisplayName("회원에게 쿠폰을 발급한다.")
    @Test
    void create() {
        // given
        Coupon coupon = couponRepository.save(Coupon.builder()
                .name("coupon")
                .discountAmount(1000)
                .minimumOrderAmount(10000)
                .category(CouponCategory.FASHION)
                .validFrom(LocalDateTime.now())
                .validTo(LocalDateTime.now().plusDays(7))
                .build());

        // when
        MemberCoupon saved = memberCouponService.create(member, coupon.getId());

        // then
        assertThat(saved.getCouponId()).isEqualTo(coupon.getId());
        assertThat(saved.getMember().getId()).isEqualTo(member.getId());
    }

    @DisplayName("쿠폰은 회원당 5장까지 발급받을 수 있다.")
    @Test
    void create_whenExceededIssuableCount() {
        // given
        Coupon coupon = couponRepository.save(Coupon.builder()
                .name("coupon")
                .discountAmount(1000)
                .minimumOrderAmount(10000)
                .category(CouponCategory.FASHION)
                .validFrom(LocalDateTime.now())
                .validTo(LocalDateTime.now().plusDays(7))
                .build());

        for (int i = 0; i < 5; i++) {
            memberCouponRepository.save(MemberCoupon.builder()
                    .couponId(coupon.getId())
                    .member(member)
                    .build());
        }

        // when, then
        assertThatThrownBy(() -> memberCouponService.create(member, coupon.getId()))
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("존재하지 않는 쿠폰은 회원에게 발급할 수 없다.")
    @Test
    void create_whenCouponNotExist() {
        assertThatThrownBy(() -> memberCouponService.create(member, getNotExistCouponId()))
                .isInstanceOf(CouponException.class);
    }


    private long getNotExistCouponId() {
        List<Long> existingCouponIds = couponRepository.findAll().stream()
                .map(Coupon::getId)
                .toList();

        Random random = new Random();
        long randomId;

        do {
            randomId = Math.abs(random.nextLong());
        } while (existingCouponIds.contains(randomId));

        return randomId;
    }
}
