package coupon.service;

import coupon.domain.MemberCoupon;
import coupon.dto.MemberCouponRequest;
import coupon.dto.MemberCouponResponse;
import coupon.repository.CouponRepository;
import coupon.repository.MemberCouponRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberCouponService {

    private static final Logger log = LoggerFactory.getLogger(MemberCouponService.class);
    public static final int MAXIMUM_ISSUABLE_COUPONS_COUNT = 5;

    private final MemberCouponRepository memberCouponRepository;
    private final CouponRepository couponRepository;

    @Transactional
    public MemberCoupon issueCoupon(MemberCouponRequest memberCouponRequest) {
        validateIssuable(memberCouponRequest.memberId());
        return memberCouponRepository.save(memberCouponRequest.toMemberCoupon());
    }

    private void validateIssuable(Long memberId) {
        int issuedCouponCount = memberCouponRepository.countByMemberId(memberId);
        if (issuedCouponCount > MAXIMUM_ISSUABLE_COUPONS_COUNT) {
            throw new IllegalArgumentException("해당 사용자에게 발행할 수 있는 쿠폰의 최대 개수를 초과했습니다.");
        }
    }

    @Transactional
    public List<MemberCouponResponse> getIssuedMemberCoupons(Long memberId) {
        log.info("Find issued member coupons by member id: {}", memberId);

        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMemberId(memberId);
        return memberCoupons.stream()
                .map(memberCoupon -> MemberCouponResponse.of(couponRepository.findById(memberCoupon.getCouponId())
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다.")), memberCoupon))
                .toList();
    }
}
