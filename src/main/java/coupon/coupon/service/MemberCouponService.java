package coupon.coupon.service;

import coupon.coupon.domain.MemberCoupon;
import coupon.coupon.domain.MemberCouponRepository;
import coupon.coupon.dto.MemberCouponCreateRequest;
import coupon.coupon.dto.MemberCouponResponse;
import coupon.coupon.dto.MyCouponsRequest;
import coupon.coupon.exception.CouponApplicationException;
import coupon.member.domain.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberCouponService {

    private static final int MAX_ISSUE_COUNT = 5;

    private final MemberCouponRepository memberCouponRepository;
    private final MemberRepository memberRepository;
    private final CouponService couponService;

    @Transactional
    public MemberCouponResponse createMemberCoupon(final MemberCouponCreateRequest memberCouponCreateRequest) {
        final var member = memberRepository.findById(memberCouponCreateRequest.memberId())
                .orElseThrow(() -> new CouponApplicationException("존재하지 않는 멤버입니다"));
        final var couponResponse = couponService.getCoupon(memberCouponCreateRequest.couponId());

        final var memberCouponCount = memberCouponRepository.countByOwnerAndCouponId(member, couponResponse.id());
        if (memberCouponCount > MAX_ISSUE_COUNT) {
            throw new CouponApplicationException("같은 쿠폰은 " + MAX_ISSUE_COUNT + "횟수 이상 발급할 수 없습니다");
        }

        final var memberCoupon = memberCouponRepository.save(new MemberCoupon(member, couponResponse.id()));
        return MemberCouponResponse.from(memberCoupon);
    }

    @Transactional(readOnly = true)
    public List<MemberCouponResponse> getMyCoupons(final MyCouponsRequest myCouponsRequest) {
        final var member = memberRepository.findById(myCouponsRequest.memberId())
                .orElseThrow(() -> new CouponApplicationException("존재하지 않는 멤버입니다"));

        final var couponsOwnedByMember = memberCouponRepository.findMemberCouponByOwner(member);

        return couponsOwnedByMember.stream()
                .map(MemberCouponResponse::from)
                .toList();
    }
}
