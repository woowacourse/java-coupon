package coupon.member.sevice;

import coupon.coupon.domain.CouponEntity;
import coupon.coupon.domain.MemberCoupon;
import coupon.coupon.dto.MemberCouponRequest;
import coupon.coupon.dto.MemberCouponResponse;
import coupon.coupon.repository.CouponRepository;
import coupon.coupon.repository.MemberCouponRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    public static final int MAX_SAME_COUPON_COUNT = 5;

    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;

    @Transactional
    public long issueCoupon(MemberCouponRequest memberCouponRequest) {
        validateSameCouponCount(memberCouponRequest);

        MemberCoupon issuedMemberCoupon = memberCouponRepository.save(
                new MemberCoupon(
                        memberCouponRequest.couponId(),
                        memberCouponRequest.memberId(),
                        false,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusDays(7).withHour(0).withMinute(0).withSecond(0).withNano(0)
                ));
        return issuedMemberCoupon.getId();
    }

    @Transactional(readOnly = true)
    public List<MemberCouponResponse> getCouponByMember(long memberId) {
        return memberCouponRepository.findAllByMemberId(memberId)
                .stream()
                .map(memberCoupon -> {
                    CouponEntity coupon = couponRepository.findById(memberCoupon.getCouponId())
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));
                    return MemberCouponResponse.fromDomain(coupon, memberCoupon);
                })
                .toList();
    }

    private void validateSameCouponCount(MemberCouponRequest memberCouponRequest) {
        int sameMemberCouponCount = memberCouponRepository.countAllByMemberIdAndCouponId(
                memberCouponRequest.memberId(), memberCouponRequest.couponId()
        );

        if (sameMemberCouponCount >= MAX_SAME_COUPON_COUNT) {
            throw new IllegalArgumentException(String.format("동일한 쿠폰은 %d장까지 발급할 수 있어요.", MAX_SAME_COUPON_COUNT));
        }
    }
}
