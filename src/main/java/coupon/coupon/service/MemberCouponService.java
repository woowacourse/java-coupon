package coupon.coupon.service;

import coupon.coupon.domain.CouponRepository;
import coupon.coupon.domain.MemberCoupon;
import coupon.coupon.domain.MemberCouponRepository;
import coupon.coupon.dto.MemberCouponCreateRequest;
import coupon.coupon.exception.CouponApplicationException;
import coupon.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberCouponService {

    private static final int MAX_ISSUE_COUNT = 5;

    private final MemberCouponRepository memberCouponRepository;
    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;

    @Transactional
    public MemberCoupon createMemberCoupon(final MemberCouponCreateRequest memberCouponCreateRequest) {
        final var member = memberRepository.findById(memberCouponCreateRequest.memberId())
                .orElseThrow(() -> new CouponApplicationException("존재하지 않는 멤버입니다"));
        final var coupon = couponRepository.findById(memberCouponCreateRequest.couponId())
                .orElseThrow(() -> new CouponApplicationException("존재하지 않는 쿠폰입니다"));

        final var memberCouponCount = memberCouponRepository.countByMemberAndCoupon(member, coupon);
        if (memberCouponCount > MAX_ISSUE_COUNT) {
            throw new CouponApplicationException("같은 쿠폰은 " + MAX_ISSUE_COUNT + "횟수 이상 발급할 수 없습니다");
        }

        final var memberCoupon = new MemberCoupon(member, coupon);
        return memberCouponRepository.save(memberCoupon);
    }
}
