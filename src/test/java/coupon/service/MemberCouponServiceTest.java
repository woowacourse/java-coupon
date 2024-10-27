package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.domain.coupon.Category;
import coupon.domain.coupon.Coupon;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.repository.MemberRepository;

@SpringBootTest
@Sql(scripts = {"/schema.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void init() {
        memberCouponRepository.deleteAll();
        memberRepository.deleteAll();
        couponRepository.deleteAll();
        memberCouponRepository.flush();
        memberRepository.flush();
        couponRepository.flush();
        clearAllCouponsCache();
    }

    @CacheEvict(value = "coupon", allEntries = true)
    public void clearAllCouponsCache() {

    }

    @DisplayName("회원마다 가능한 수량 내에서 한 종류의 쿠폰을 여러 개 발급할 수 있다.")
    @Test
    void create() {
        // given
        Member member = new Member("sancho");
        memberRepository.save(member);
        Coupon coupon = new Coupon("create-test-coupon", 1000, 10000, LocalDate.now(), LocalDate.now(), Category.ELECTRONICS);
        couponRepository.save(coupon);
        System.out.println("###모든쿠폰갯수 = " + couponRepository.findAll().size());
        System.out.println("###캐시에서찾은쿠폰 = " + couponService.getCoupon(1L));
        System.out.println("###DB에서 찾은 쿠폰 = " + couponRepository.findById(1L).get());

        long id1 = memberCouponService.create(member, coupon.getId(), LocalDate.now());
        long id2 = memberCouponService.create(member, coupon.getId(), LocalDate.now());
        long id3 = memberCouponService.create(member, coupon.getId(), LocalDate.now());
        long id4 = memberCouponService.create(member, coupon.getId(), LocalDate.now());

        // when
        long memberCouponId = memberCouponService.create(member, coupon.getId(), LocalDate.now());
        System.out.println("###" + memberCouponId);
        System.out.println("###" + memberCouponRepository.findAllByMember(member).size());

        // then
        assertThat(memberCouponRepository.findById(memberCouponId)).isNotEmpty();
    }

    @DisplayName("가능한 수량을 초과하여 한 종류의 쿠폰을 발급하려고 하면 예외가 발생한다.")
    @Test
    void createFail() {
        System.out.println("################생성예외테스트시작");
        // given
        Member member = new Member("sancho");
        memberRepository.save(member);
        Coupon coupon = new Coupon("create-fail-coupon", 1000, 10000, LocalDate.now(), LocalDate.now(), Category.ELECTRONICS);
        Coupon saved = couponRepository.save(coupon);
        System.out.println("### 저장된 쿠폰" + saved);
        System.out.println("### 저장된 쿠폰들" + couponRepository.findAll());

        memberCouponService.create(member, coupon.getId(), LocalDate.now());
        memberCouponService.create(member, coupon.getId(), LocalDate.now());
        memberCouponService.create(member, coupon.getId(), LocalDate.now());
        memberCouponService.create(member, coupon.getId(), LocalDate.now());
        memberCouponService.create(member, coupon.getId(), LocalDate.now());

        // when & then
        assertThatThrownBy(() -> memberCouponService.create(member, coupon.getId(), LocalDate.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("모두 발급하셨기에 추가 발급이 불가능합니다.");
    }

    @DisplayName("회원의 쿠폰 목록을 조회한다.")
    @Test
    void getMemberCoupons() {
        // given
        memberCouponRepository.deleteAll();
        memberCouponRepository.flush();
        System.out.println("###초기 사이즈 = " + memberCouponRepository.findAll().size());

        Member member = new Member("sancho");
        memberRepository.save(member);
        Coupon coupon = new Coupon("read-test-coupon", 1000, 10000, LocalDate.now(), LocalDate.now(), Category.ELECTRONICS);
        couponRepository.save(coupon);

        long id1 = memberCouponService.create(member, coupon.getId(), LocalDate.now());
        long id2 = memberCouponService.create(member, coupon.getId(), LocalDate.now());

        System.out.println(id1);
        System.out.println(id2);

        // when
        List<MemberCoupon> memberCoupons = memberCouponService.getMemberCoupons(member);
        System.out.println("##" + memberCoupons);

        // then
        assertThat(memberCoupons.size()).isEqualTo(2);
    }
}
