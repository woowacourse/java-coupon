package coupon.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import coupon.repository.entity.CouponEntity;
import coupon.repository.entity.MemberCouponEntity;
import coupon.repository.entity.MemberEntity;

@Transactional
@SpringBootTest
class MemberCouponRepositoryTest {

    @Autowired
    private MemberCouponRepository memberCouponRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponRepository couponRepository;

    private long memberId;
    private long couponId;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
        couponRepository.deleteAll();
        memberCouponRepository.deleteAll();

        MemberEntity memberEntity = memberRepository.save(new MemberEntity("도도"));
        CouponEntity couponEntity = couponRepository.save(
                new CouponEntity(
                        "할인 쿠폰",
                        9000L,
                        500L,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusDays(7)
                )
        );
        memberId = memberEntity.getId();
        couponId = couponEntity.getId();
    }

    @DisplayName("멤버의 특정 쿠폰 개수를 반환한다.")
    @Test
    void count_by_member_id_and_coupon_id() {
        // given
        saveMemberCoupon();
        saveMemberCoupon();

        // when
        final long actual = memberCouponRepository.countByMemberIdAndCouponId(memberId, couponId);

        // then
        assertThat(actual).isEqualTo(2);
    }

    protected void saveMemberCoupon() {
        MemberCouponEntity memberCouponEntity = new MemberCouponEntity(
                couponId,
                memberId,
                false,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7)
        );
        memberCouponRepository.save(memberCouponEntity);
    }
}
