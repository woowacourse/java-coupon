package coupon.service;

import coupon.data.MemberCoupon;
import coupon.data.repository.CouponRepository;
import coupon.data.repository.MemberCouponRepository;
import coupon.domain.coupon.CouponMapper;
import coupon.domain.member.MemberCouponMapper;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;


    public void issue(long memberId, long couponId) {
        List<MemberCoupon> all = memberCouponRepository.findAllByMemberIdAndCouponId(
                memberId, couponId);

        if (all.size() >= 5) {
            throw new IllegalStateException("size exceeded");
        }

        coupon.domain.member.MemberCoupon memberCoupon = new coupon.domain.member.MemberCoupon(couponId, memberId,
                false, LocalDateTime.now(),
                LocalDateTime.now());
        memberCouponRepository.save(MemberCouponMapper.toEntity(memberCoupon));
    }

    public List<CouponResponse> findByMemberId(long memberId) {
        List<MemberCoupon> allByMemberId = memberCouponRepository.findAllByMemberId(memberId);
        return allByMemberId.stream()
                .map(MemberCouponMapper::fromEntity)
                .map(r -> new CouponResponse(CouponMapper.fromEntity(couponRepository.findById(r.getCouponId()).get()),
                        r))
                .toList();
    }
}
