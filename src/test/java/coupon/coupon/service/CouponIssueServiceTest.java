package coupon.coupon.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import coupon.coupon.domain.Category;
import coupon.coupon.domain.Coupon;
import coupon.coupon.domain.IssuedCoupon;
import coupon.coupon.repository.CouponEntity;
import coupon.coupon.repository.CouponRepository;
import coupon.coupon.repository.IssuedCouponEntity;
import coupon.coupon.repository.IssuedCouponRepository;
import coupon.member.domain.Member;
import coupon.member.repository.MemberEntity;
import coupon.member.repository.MemberRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ServiceTest
class CouponIssueServiceTest {

    @Autowired
    private IssuedCouponRepository issuedCouponRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponIssueService couponIssueService;

    @Autowired
    private CouponIssuePolicyProperties couponIssuePolicyProperties;

    @Test
    void 한_사람이_같은_쿠폰을_제한보다_많이_발급할_수_없다() {
        Member member = new Member("마크");
        MemberEntity memberEntity = memberRepository.save(MemberEntity.from(member));

        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 2);
        Coupon coupon = new Coupon("쿠폰", Category.FOOD, 1_000L, 10_000L, List.of(), startDate, endDate);
        CouponEntity couponEntity = couponRepository.save(CouponEntity.from(coupon));

        IssuedCoupon issuedCoupon = new IssuedCoupon(coupon, startDate.atTime(15, 30));
        for (int i = 0; i < couponIssuePolicyProperties.issueLimit(); i++) {
            IssuedCouponEntity entity = IssuedCouponEntity.from(memberEntity.getId(), couponEntity.getId(), issuedCoupon);
            issuedCouponRepository.save(entity);
        }
        assertThatThrownBy(() -> couponIssueService.issueCoupon(memberEntity.getId(), couponEntity.getId()))
                .isInstanceOf(IssuedCouponLimitExceededException.class);
    }
}
