package coupon.coupon.service;

import coupon.coupon.domain.IssuedCoupon;
import coupon.coupon.repository.CouponEntity;
import coupon.coupon.repository.CouponRepository;
import coupon.coupon.repository.IssuedCouponEntity;
import coupon.coupon.repository.IssuedCouponRepository;
import coupon.member.repository.MemberRepository;
import java.time.Clock;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(CouponIssuePolicyProperties.class)
public class CouponIssueService {

    private final CouponIssuePolicyProperties issueProperties;

    private final CouponRepository couponRepository;
    private final IssuedCouponRepository issuedCouponRepository;
    private final MemberRepository memberRepository;

    private final Clock clock;

    @Transactional(readOnly = false)
    public IssuedCouponEntity issueCoupon(long memberId, long couponId) {
        CouponEntity couponEntity = couponRepository.findByIdOrThrow(couponId);
        memberRepository.findByIdOrThrow(memberId);

        int issuedCouponByMember = issuedCouponRepository.countIssuedByMemberId(memberId, couponEntity.getId());
        if (issuedCouponByMember >= issueProperties.issueLimit()) {
            throw new IssuedCouponLimitExceededException(memberId, issueProperties.issueLimit());
        }

        IssuedCoupon issuedCoupon = new IssuedCoupon(couponEntity.toDomain(), LocalDateTime.now(clock));
        return issuedCouponRepository.save(IssuedCouponEntity.from(memberId, couponEntity.getId(), issuedCoupon));
    }
}
