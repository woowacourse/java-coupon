package coupon.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import coupon.domain.MemberCoupon;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.repository.MemberRepository;
import coupon.repository.entity.CouponEntity;
import coupon.repository.entity.MemberCouponEntity;
import coupon.repository.entity.MemberEntity;
import coupon.service.dto.CouponInfoResponse;

@Transactional
@SpringBootTest
class MemberCouponServiceTest {

    @Autowired
    private MemberCouponService memberCouponService;

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponRepository couponRepository;

    private CouponEntity couponEntity;
    private long memberId;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        couponRepository.deleteAll();
        memberCouponRepository.deleteAll();

        MemberEntity memberEntity = memberRepository.save(new MemberEntity("도도"));
        memberId = memberEntity.getId();
        couponEntity = couponRepository.save(
                new CouponEntity(
                        "할인 쿠폰",
                        9000L,
                        500L,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusDays(7)
                )
        );
    }

    @DisplayName("특정 멤버에게 쿠폰을 발행한다.")
    @Test
    void provide_coupon_to_member() {
        // given
        final MemberCoupon memberCoupon = new MemberCoupon(
                memberId,
                couponEntity.getId(),
                false,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7)
        );

        // when & then
        assertThatCode(() -> memberCouponService.provideCoupon(memberCoupon))
                .doesNotThrowAnyException();
    }

    @DisplayName("특정 멤버의 모든 쿠폰과 발급 내역을 조회한다.")
    @Test
    void get_all_coupons_by_member() {
        // given
        saveMemberCoupon();

        // when
        final List<CouponInfoResponse> actual = memberCouponService.getCouponsByMember(memberId);

        // then
        assertThat(actual.getFirst().couponName())
                .isEqualTo(couponEntity.getName());
    }

    protected void saveMemberCoupon() {
        MemberCouponEntity memberCouponEntity = new MemberCouponEntity(
                couponEntity.getId(),
                memberId,
                false,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7)
        );
        memberCouponRepository.save(memberCouponEntity);
    }
}
