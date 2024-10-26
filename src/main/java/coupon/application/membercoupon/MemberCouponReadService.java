package coupon.application.membercoupon;

import coupon.domain.membercoupon.repository.MemberCouponRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberCouponReadService {

    private final MemberCouponRepository memberCouponRepository;

    public List<MemberCouponResponse> findAllByMember(Long memberId) {
        return memberCouponRepository.findAllByMemberId(memberId).stream()
                .map(MemberCouponResponse::from)
                .toList();
    }

    public List<MemberCouponResponse> findAllByMemberIdAndCouponId(Long memberId, Long couponId) {
        return memberCouponRepository.findAllByMemberIdAndCouponId(memberId, couponId).stream()
                .map(MemberCouponResponse::from)
                .toList();
    }
}
