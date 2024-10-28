package coupon.service;

import coupon.aop.ReplicationDelayHandler;
import coupon.domain.MemberCoupon;
import coupon.domain.dto.MemberCouponCreateRequest;
import coupon.repository.MemberCouponRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;

    @Transactional
    public MemberCoupon create(MemberCouponCreateRequest request) {
        if (memberCouponRepository.countByCouponIdAndMemberId(request.couponId(), request.memberId()) > 5) {
            throw new IllegalArgumentException("한 사람은 동일한 쿠폰을 5장까지 발급받을 수 있습니다.");
        }
        MemberCoupon coupon = request.toEntity();
        return memberCouponRepository.save(coupon);
    }

    @ReplicationDelayHandler
    public MemberCoupon get(Long memberCouponId) {
        return memberCouponRepository.findById(memberCouponId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 쿠폰이 존재하지 않습니다."));
    }
}
