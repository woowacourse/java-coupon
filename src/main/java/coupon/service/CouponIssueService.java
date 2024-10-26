package coupon.service;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.MemberCoupon;
import coupon.domain.member.Member;
import coupon.exception.CouponException;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import coupon.repository.MemberRepository;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CouponIssueService {

    public static final int MAX_COUPON_ISSUANCE_COUNT = 5;
    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;
    private final MemberCouponRepository memberCouponRepository;

    public CouponIssueService(
            CouponRepository couponRepository,
            MemberRepository memberRepository,
            MemberCouponRepository memberCouponRepository
    ) {
        this.couponRepository = couponRepository;
        this.memberRepository = memberRepository;
        this.memberCouponRepository = memberCouponRepository;
    }

    public void issue(long memberId, long couponId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new CouponException("존재하지 않는 회원입니다."));
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new CouponException("존재하지 않는 쿠폰입니다."));
        LocalDateTime issuanceTime = LocalDateTime.now();
        coupon.validateDateCouponIssuance(issuanceTime);
        validateIssuedCount(member, coupon);
        memberCouponRepository.save(new MemberCoupon(member.getId(), coupon.getId(), issuanceTime));
    }

    private void validateIssuedCount(Member member, Coupon coupon) {
        int issuedCouponCount = memberCouponRepository.countByMemberIdAndCouponId(member.getId(), coupon.getId());
        if (issuedCouponCount >= MAX_COUPON_ISSUANCE_COUNT) {
            throw new CouponException(String.format(
                    "해당 쿠폰 발급 가능 갯수 %d개를 초과 했습니다.",
                    MAX_COUPON_ISSUANCE_COUNT)
            );
        }
    }
}
