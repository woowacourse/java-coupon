package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.entity.MemberCouponEntity;
import coupon.repository.MemberCouponRepository;
import coupon.service.support.CacheService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;
    private final CacheService cacheService;

    @Transactional
    public MemberCoupon create(Member member, Coupon coupon) {
        List<MemberCoupon> issuedMemberCoupons = getMemberCoupons(member, coupon);
        validateMemberCouponLimit(issuedMemberCoupons);
        MemberCoupon memberCoupon = MemberCoupon.issue(member, coupon);
        memberCouponRepository.save(new MemberCouponEntity(memberCoupon));
        return memberCoupon;
    }

    private List<MemberCoupon> getMemberCoupons(Member member, Coupon coupon) {
        return memberCouponRepository.findAllByMemberIdAndCouponId(member.getId(), coupon.getId()).stream()
                .map(memberCoupon -> memberCoupon.toDomain(member, coupon))
                .toList();
    }

    private void validateMemberCouponLimit(List<MemberCoupon> memberCoupons) {
        int memberCouponLimit = 5;
        if (memberCoupons.size() >= memberCouponLimit) {
            throw new IllegalArgumentException(String.format("이미 %d장의 쿠폰을 발급받았습니다.", memberCouponLimit));
        }
    }

    public List<MemberCoupon> findAllByMember(Member member) {
        List<MemberCouponEntity> issuedMemberCoupons = memberCouponRepository.findAllByMemberId(member.getId());
        return issuedMemberCoupons.stream()
                .map(memberCoupon -> memberCoupon.toDomain(member, cacheService.getCoupon(memberCoupon)))
                .toList();
    }
}
